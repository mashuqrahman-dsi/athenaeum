/*
 * This file is generated by jOOQ.
*/
package com.mashuq.athenaeum.domain.athenaeum;

import javax.annotation.Generated;

import com.mashuq.athenaeum.domain.athenaeum.tables.Book;
import com.mashuq.athenaeum.domain.athenaeum.tables.Information;
import com.mashuq.athenaeum.domain.athenaeum.tables.Request;

/**
 * Convenience access to all tables in ATHENAEUM
 */
@Generated(value = {"http://www.jooq.org",
		"jOOQ version:3.10.5"}, comments = "This class is generated by jOOQ")
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Tables {

	/**
	 * The table <code>ATHENAEUM.BOOK</code>.
	 */
	public static final Book BOOK = com.mashuq.athenaeum.domain.athenaeum.tables.Book.BOOK;

	/**
	 * The table <code>ATHENAEUM.INFORMATION</code>.
	 */
	public static final Information INFORMATION = com.mashuq.athenaeum.domain.athenaeum.tables.Information.INFORMATION;

	/**
	 * The table <code>ATHENAEUM.REQUEST</code>.
	 */
	public static final Request REQUEST = com.mashuq.athenaeum.domain.athenaeum.tables.Request.REQUEST;
}
