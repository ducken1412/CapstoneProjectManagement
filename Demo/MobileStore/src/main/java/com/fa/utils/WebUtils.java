package com.fa.utils;

public class WebUtils {


	public static int getPages(int total, int totalperpage) {
		int pages = total / totalperpage;
		if (total % totalperpage != 0) {
			pages++;
		}
		return pages;
	}

	public static String getPaging(int total, String url, int page, int totalperpage) {
		int pages = WebUtils.getPages(total, totalperpage);

		if(url.contains("?")) {
			url +="&";
		}else {
			url +="?";
		}
		if (page > pages||page<1) {
			page = 1;
		}
		int count = 0;
		String tmp = "";
		for (int j = page - 1; j > 0; j--) {
			if (++count > 3) {
				break;
			}
			tmp = "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "page=" + j + "\" >" + j + "</a></li>" + tmp;
		}

		if (page > 4) {
			tmp = "<li class=\"page-item\"><a class=\"page-link\" href=\"#\">...</a></li>" + tmp;
			tmp = "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "page=1\" >" + 1 + "</a></li>" + tmp;
		}

		tmp += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\" >" + page + "</a></li>";
		count = 0;
		for (int j = page + 1; j <= pages; j++) {
			if (++count > 3) {
				break;
			}
			tmp += "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "page=" + j + "\" >" + j + "</a></li>";
		}

		if (pages > 3 + page) {
			tmp += "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url  + "?page=" + page + "\">...</a></li>";
			tmp += "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "&page=" + pages + "\" >" + pages + "</a></li>";
		}

		// tmp_final
		String tmp_final = "<ul class=\"pagination \">";
		if(page!=1) {
			tmp_final += "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "page=1\" >First</a></li>";
		}
		tmp_final += tmp;
		if(page!=pages) {
			tmp_final += "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "page=" + pages + "\" >Last</a></li>";
		}
		
		tmp_final += "</ul><!--/.pagination-->";

		return tmp_final;
	}

}