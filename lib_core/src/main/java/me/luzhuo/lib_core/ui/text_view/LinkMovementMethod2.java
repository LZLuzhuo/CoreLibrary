package me.luzhuo.lib_core.ui.text_view;

import android.graphics.RectF;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * Handles URL clicks on TextViews. Unlike the default implementation, this:
 * <p>
 * <ul>
 * <li>Reliably applies a highlight color on links when they're touched.</li>
 * <li>Let's you handle single and long clicks on URLs</li>
 * <li>Correctly identifies focused URLs (Unlike the default implementation where a click is registered even if it's
 * made outside of the URL's bounds if there is no more text in that direction.)</li>
 * </ul>
 *
 * 使用:
 * <pre>
 * textview.setMovementMethod(new LinkMovementMethod2().setOnLinkClickListener((textView, url) -> {
 *     Toast.makeText(MainActivity.this, "click: " + url, Toast.LENGTH_SHORT).show();
 *     return true;
 * }).setOnLinkLongClickListener((textView, url) -> {
 *     Toast.makeText(MainActivity.this, "long click: " + url, Toast.LENGTH_SHORT).show();
 *     return true;
 * }));
 * </pre>
 *
 * 如果在 RecyclerView 中使用, 需要写在 ViewHolder 的 bindData() 里. <p>
 * 注意: 请使用 AppCompatTextView
 *
 * <p>
 * 来源: https://github.com/saket/Better-Link-Movement-Method
 * 开源协议: Apache-2.0 license
 * 修改:
 * 1. 删除所有 static 方法
 */
public class LinkMovementMethod2 extends LinkMovementMethod {

    private OnLinkClickListener onLinkClickListener;
    private OnLinkLongClickListener onLinkLongClickListener;
    private final RectF touchedLineBounds = new RectF();
    private boolean isUrlHighlighted;
    private ClickableSpan clickableSpanUnderTouchOnActionDown;
    private int activeTextViewHashcode;
    private LongPressTimer ongoingLongPressTimer;
    private boolean wasLongPressRegistered;

    public interface OnLinkClickListener {
        /**
         * @param textView The TextView on which a click was registered.
         * @param url      The clicked URL.
         * @return True if this click was handled. False to let Android handle the URL.
         */
        boolean onClick(TextView textView, String url);
    }

    public interface OnLinkLongClickListener {
        /**
         * @param textView The TextView on which a long-click was registered.
         * @param url      The long-clicked URL.
         * @return True if this long-click was handled. False to let Android handle the URL (as a short-click).
         */
        boolean onLongClick(TextView textView, String url);
    }

    /**
     * Set a listener that will get called whenever any link is clicked on the TextView.
     */
    public LinkMovementMethod2 setOnLinkClickListener(OnLinkClickListener clickListener) {
        this.onLinkClickListener = clickListener;
        return this;
    }

    /**
     * Set a listener that will get called whenever any link is clicked on the TextView.
     */
    public LinkMovementMethod2 setOnLinkLongClickListener(OnLinkLongClickListener longClickListener) {
        this.onLinkLongClickListener = longClickListener;
        return this;
    }

    @Override
    public boolean onTouchEvent(final TextView textView, Spannable text, MotionEvent event) {
        if (activeTextViewHashcode != textView.hashCode()) {
            // Bug workaround: TextView stops calling onTouchEvent() once any URL is highlighted.
            // A hacky solution is to reset any "autoLink" property set in XML. But we also want
            // to do this once per TextView.
            activeTextViewHashcode = textView.hashCode();
            textView.setAutoLinkMask(0);
        }

        final ClickableSpan clickableSpanUnderTouch = findClickableSpanUnderTouch(textView, text, event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            clickableSpanUnderTouchOnActionDown = clickableSpanUnderTouch;
        }
        final boolean touchStartedOverAClickableSpan = clickableSpanUnderTouchOnActionDown != null;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (clickableSpanUnderTouch != null) {
                    highlightUrl(textView, clickableSpanUnderTouch, text);
                }

                if (touchStartedOverAClickableSpan && onLinkLongClickListener != null) {
                    LongPressTimer.OnTimerReachedListener longClickListener = new LongPressTimer.OnTimerReachedListener() {
                        @Override
                        public void onTimerReached() {
                            wasLongPressRegistered = true;
                            textView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                            removeUrlHighlightColor(textView);
                            dispatchUrlLongClick(textView, clickableSpanUnderTouch);
                        }
                    };
                    startTimerForRegisteringLongClick(textView, longClickListener);
                }
                return touchStartedOverAClickableSpan;

            case MotionEvent.ACTION_UP:
                // Register a click only if the touch started and ended on the same URL.
                if (!wasLongPressRegistered && touchStartedOverAClickableSpan && clickableSpanUnderTouch == clickableSpanUnderTouchOnActionDown) {
                    dispatchUrlClick(textView, clickableSpanUnderTouch);
                }
                cleanupOnTouchUp(textView);

                // Consume this event even if we could not find any spans to avoid letting Android handle this event.
                // Android's TextView implementation has a bug where links get clicked even when there is no more text
                // next to the link and the touch lies outside its bounds in the same direction.
                return touchStartedOverAClickableSpan;

            case MotionEvent.ACTION_CANCEL:
                cleanupOnTouchUp(textView);
                return false;

            case MotionEvent.ACTION_MOVE:
                // Stop listening for a long-press as soon as the user wanders off to unknown lands.
                if (clickableSpanUnderTouch != clickableSpanUnderTouchOnActionDown) {
                    removeLongPressCallback(textView);
                }

                if (!wasLongPressRegistered) {
                    // Toggle highlight.
                    if (clickableSpanUnderTouch != null) {
                        highlightUrl(textView, clickableSpanUnderTouch, text);
                    } else {
                        removeUrlHighlightColor(textView);
                    }
                }

                return touchStartedOverAClickableSpan;

            default:
                return false;
        }
    }

    private void cleanupOnTouchUp(TextView textView) {
        wasLongPressRegistered = false;
        clickableSpanUnderTouchOnActionDown = null;
        removeUrlHighlightColor(textView);
        removeLongPressCallback(textView);
    }

    /**
     * Determines the touched location inside the TextView's text and returns the ClickableSpan found under it (if any).
     *
     * @return The touched ClickableSpan or null.
     */
    protected ClickableSpan findClickableSpanUnderTouch(TextView textView, Spannable text, MotionEvent event) {
        // So we need to find the location in text where touch was made, regardless of whether the TextView
        // has scrollable text. That is, not the entire text is currently visible.
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        // Ignore padding.
        touchX -= textView.getTotalPaddingLeft();
        touchY -= textView.getTotalPaddingTop();

        // Account for scrollable text.
        touchX += textView.getScrollX();
        touchY += textView.getScrollY();

        final Layout layout = textView.getLayout();
        final int touchedLine = layout.getLineForVertical(touchY);
        final int touchOffset = layout.getOffsetForHorizontal(touchedLine, touchX);

        touchedLineBounds.left = layout.getLineLeft(touchedLine);
        touchedLineBounds.top = layout.getLineTop(touchedLine);
        touchedLineBounds.right = layout.getLineWidth(touchedLine) + touchedLineBounds.left;
        touchedLineBounds.bottom = layout.getLineBottom(touchedLine);

        if (touchedLineBounds.contains(touchX, touchY)) {
            // Find a ClickableSpan that lies under the touched area.
            final Object[] spans = text.getSpans(touchOffset, touchOffset, ClickableSpan.class);
            for (final Object span : spans) {
                if (span instanceof ClickableSpan) {
                    return (ClickableSpan) span;
                }
            }
            // No ClickableSpan found under the touched location.
            return null;

        } else {
            // Touch lies outside the line's horizontal bounds where no spans should exist.
            return null;
        }
    }

    /**
     * Adds a background color span at <var>clickableSpan</var>'s location.
     */
    protected void highlightUrl(TextView textView, ClickableSpan clickableSpan, Spannable text) {
        if (isUrlHighlighted) {
            return;
        }
        isUrlHighlighted = true;

        int spanStart = text.getSpanStart(clickableSpan);
        int spanEnd = text.getSpanEnd(clickableSpan);
        BackgroundColorSpan highlightSpan = new BackgroundColorSpan(textView.getHighlightColor());
        text.setSpan(highlightSpan, spanStart, spanEnd, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setTag(1010101010, highlightSpan);

        Selection.setSelection(text, spanStart, spanEnd);
    }

    /**
     * Removes the highlight color under the Url.
     */
    protected void removeUrlHighlightColor(TextView textView) {
        if (!isUrlHighlighted) {
            return;
        }
        isUrlHighlighted = false;

        Spannable text = (Spannable) textView.getText();
        BackgroundColorSpan highlightSpan = (BackgroundColorSpan) textView.getTag(1010101010);
        text.removeSpan(highlightSpan);

        Selection.removeSelection(text);
    }

    protected void startTimerForRegisteringLongClick(TextView textView, LongPressTimer.OnTimerReachedListener longClickListener) {
        ongoingLongPressTimer = new LongPressTimer();
        ongoingLongPressTimer.setOnTimerReachedListener(longClickListener);
        textView.postDelayed(ongoingLongPressTimer, ViewConfiguration.getLongPressTimeout());
    }

    /**
     * Remove the long-press detection timer.
     */
    protected void removeLongPressCallback(TextView textView) {
        if (ongoingLongPressTimer != null) {
            textView.removeCallbacks(ongoingLongPressTimer);
            ongoingLongPressTimer = null;
        }
    }

    protected void dispatchUrlClick(TextView textView, ClickableSpan clickableSpan) {
        ClickableSpanWithText clickableSpanWithText = ClickableSpanWithText.ofSpan(textView, clickableSpan);
        boolean handled = onLinkClickListener != null && onLinkClickListener.onClick(textView, clickableSpanWithText.text());

        if (!handled) {
            // Let Android handle this click.
            clickableSpanWithText.span().onClick(textView);
        }
    }

    protected void dispatchUrlLongClick(TextView textView, ClickableSpan clickableSpan) {
        ClickableSpanWithText clickableSpanWithText = ClickableSpanWithText.ofSpan(textView, clickableSpan);
        boolean handled = onLinkLongClickListener != null && onLinkLongClickListener.onLongClick(textView, clickableSpanWithText.text());

        if (!handled) {
            // Let Android handle this long click as a short-click.
            clickableSpanWithText.span().onClick(textView);
        }
    }

    protected static final class LongPressTimer implements Runnable {
        private OnTimerReachedListener onTimerReachedListener;

        protected interface OnTimerReachedListener {
            void onTimerReached();
        }

        @Override
        public void run() {
            onTimerReachedListener.onTimerReached();
        }

        public void setOnTimerReachedListener(OnTimerReachedListener listener) {
            onTimerReachedListener = listener;
        }
    }

    /**
     * A wrapper to support all {@link ClickableSpan}s that may or may not provide URLs.
     */
    protected static class ClickableSpanWithText {
        private final ClickableSpan span;
        private final String text;

        protected static ClickableSpanWithText ofSpan(TextView textView, ClickableSpan span) {
            Spanned s = (Spanned) textView.getText();
            String text;
            if (span instanceof URLSpan) {
                text = ((URLSpan) span).getURL();
            } else {
                int start = s.getSpanStart(span);
                int end = s.getSpanEnd(span);
                text = s.subSequence(start, end).toString();
            }
            return new ClickableSpanWithText(span, text);
        }

        protected ClickableSpanWithText(ClickableSpan span, String text) {
            this.span = span;
            this.text = text;
        }

        protected ClickableSpan span() {
            return span;
        }

        protected String text() {
            return text;
        }
    }
}