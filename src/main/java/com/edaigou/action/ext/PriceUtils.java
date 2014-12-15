package com.edaigou.action.ext;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.edaigou.entity.ItemFilters;

public class PriceUtils {
	
	
	public static String getPrice(String html) {
		Document document = Jsoup.parse(html);

		Element element = document.getElementById("J_Price");

		return element != null ? element.text() : "0";
	}

	public static Double getMinPrice(String html, String orgTitle,
			List<ItemFilters> itemFilters) {
		// IE 11
		if (html.contains("g_page_config")) {
			return getIE11MinPrice(html, orgTitle, itemFilters);
		} else {
			return getIE8MinPrice(html, orgTitle, itemFilters);
		}
	}

	private static Double getIE11MinPrice(String html, String orgTitle,
			List<ItemFilters> itemFilters) {
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("item");
		Double minPrice = Double.MAX_VALUE;
		for (Element element : elements) {
			Elements eshops = element.getElementsByClass("shop");
			if (eshops == null || eshops.size() == 0) {
				continue;
			}
			Element eshop = eshops.get(0);
			String nick = eshop.text();
			boolean isFlag = false;
			for (ItemFilters filters : itemFilters) {
				if (StringUtils.equals(filters.getNick(), nick)) {
					isFlag = true;
				}
			}
			if (isFlag) {
				continue;
			}
			Elements e2s = element.getElementsByClass("title");
			if (e2s == null || e2s.size() == 0) {
				continue;
			}
			Element e2 = e2s.get(0);
			e2s = element.getElementsByClass("price");
			if (e2s == null || e2s.size() == 0) {
				continue;
			}
			Element eP2 = e2s.get(0);
			String title = e2.text();
			String price = eP2.text().replace("¥", "");
			if (!StringUtils.startsWith(org.springframework.util.StringUtils
					.trimAllWhitespace(title),
					org.springframework.util.StringUtils
							.trimAllWhitespace(orgTitle))) {
				continue;
			}
			try {
				minPrice = minPrice > Double.valueOf(price) ? Double
						.valueOf(price) : minPrice;
			} catch (Exception e) {
			}
		}

		try {
			FileUtils.write(new File("C:/Users/zoro/Desktop/a.txt"), html);
		} catch (IOException e) {
		}
		return minPrice;
	}

	private static Double getIE8MinPrice(String html, String orgTitle,
			List<ItemFilters> itemFilters) {
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("item-box");
		Double minPrice = Double.MAX_VALUE;
		for (Element element : elements) {
			Elements eshops = element.getElementsByClass("shop");
			if (eshops == null || eshops.size() == 0) {
				continue;
			}
			Element eshop = eshops.get(0);
			String nick = eshop.text();
			boolean isFlag = false;
			for (ItemFilters filters : itemFilters) {
				if (StringUtils.equals(filters.getNick(), nick)) {
					isFlag = true;
				}
			}
			if (isFlag) {
				continue;
			}
			Elements e2s = element.getElementsByClass("summary");
			if (e2s == null || e2s.size() == 0) {
				continue;
			}
			Element e2 = e2s.get(0);
			e2s = element.getElementsByClass("price");
			if (e2s == null || e2s.size() == 0) {
				continue;
			}
			Element eP2 = e2s.get(0);
			String title = e2.text();
			String price = eP2.text().replace("¥", "");
			if (!StringUtils.startsWith(org.springframework.util.StringUtils
					.trimAllWhitespace(title),
					org.springframework.util.StringUtils
							.trimAllWhitespace(orgTitle))) {
				continue;
			}
			try {
				minPrice = minPrice > Double.valueOf(price) ? Double
						.valueOf(price) : minPrice;
			} catch (Exception e) {
			}
		}
		return minPrice;
	}
}
