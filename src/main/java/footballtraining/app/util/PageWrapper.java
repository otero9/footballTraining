package footballtraining.app.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * The Class PageWrapper.
 *
 * @param <T>
 *            the generic type
 */
public class PageWrapper<T> {

	/** The Constant MAX_PAGE_ITEM_DISPLAY. */
	public static final int MAX_PAGE_ITEM_DISPLAY = 2;

	/** The page. */
	private Page<T> page;

	/** The items. */
	private List<PageItem> items;

	/** The current number. */
	private int currentNumber;

	/** The url. */
	private String url;

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Instantiates a new page wrapper.
	 *
	 * @param page
	 *            the page
	 * @param url
	 *            the url
	 */
	public PageWrapper(Page<T> page, String url) {
		this.page = page;
		this.url = url;
		items = new ArrayList<PageItem>();

		currentNumber = page.getNumber() + 1; // start from 1 to match page.page

		int start, size;
		if (page.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY) {
			start = 1;
			size = page.getTotalPages();
		} else {
			if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY / 2) {
				start = 1;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else if (currentNumber >= page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY / 2) {
				start = page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY + 1;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else {
				start = currentNumber - MAX_PAGE_ITEM_DISPLAY / 2;
				size = MAX_PAGE_ITEM_DISPLAY;
			}
		}

		for (int i = 0; i < size; i++) {
			items.add(new PageItem(start + i, (start + i) == currentNumber));
		}
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<PageItem> getItems() {
		return items;
	}

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public int getNumber() {
		return currentNumber;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public List<T> getContent() {
		return page.getContent();
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return page.getSize();
	}

	/**
	 * Gets the total pages.
	 *
	 * @return the total pages
	 */
	public int getTotalPages() {
		return page.getTotalPages();
	}

	/**
	 * Checks if is first page.
	 *
	 * @return true, if is first page
	 */
	public boolean isFirstPage() {
		return page.isFirst();
	}

	/**
	 * Checks if is last page.
	 *
	 * @return true, if is last page
	 */
	public boolean isLastPage() {
		return page.isLast();
	}

	/**
	 * Checks if is checks for previous page.
	 *
	 * @return true, if is checks for previous page
	 */
	public boolean isHasPreviousPage() {
		return page.hasPrevious();
	}

	/**
	 * Checks if is checks for next page.
	 *
	 * @return true, if is checks for next page
	 */
	public boolean isHasNextPage() {
		return page.hasNext();
	}

	/**
	 * The Class PageItem.
	 */
	public class PageItem {

		/** The number. */
		private int number;

		/** The current. */
		private boolean current;

		/**
		 * Instantiates a new page item.
		 *
		 * @param number
		 *            the number
		 * @param current
		 *            the current
		 */
		public PageItem(int number, boolean current) {
			this.number = number;
			this.current = current;
		}

		/**
		 * Gets the number.
		 *
		 * @return the number
		 */
		public int getNumber() {
			return this.number;
		}

		/**
		 * Checks if is current.
		 *
		 * @return true, if is current
		 */
		public boolean isCurrent() {
			return this.current;
		}
	}
}
