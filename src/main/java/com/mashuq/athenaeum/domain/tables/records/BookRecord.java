/*
 * This file is generated by jOOQ.
*/
package com.mashuq.athenaeum.domain.tables.records;


import com.mashuq.athenaeum.domain.tables.Book;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BookRecord extends UpdatableRecordImpl<BookRecord> implements Record5<Integer, String, String, String, String> {

    private static final long serialVersionUID = 461423402;

    /**
     * Setter for <code>PUBLIC.BOOK.BOOKID</code>.
     */
    public void setBookid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.BOOK.BOOKID</code>.
     */
    public Integer getBookid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>PUBLIC.BOOK.TITLE</code>.
     */
    public void setTitle(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.BOOK.TITLE</code>.
     */
    public String getTitle() {
        return (String) get(1);
    }

    /**
     * Setter for <code>PUBLIC.BOOK.SUBTITLE</code>.
     */
    public void setSubtitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.BOOK.SUBTITLE</code>.
     */
    public String getSubtitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>PUBLIC.BOOK.ISBN10</code>.
     */
    public void setIsbn10(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.BOOK.ISBN10</code>.
     */
    public String getIsbn10() {
        return (String) get(3);
    }

    /**
     * Setter for <code>PUBLIC.BOOK.ISBN13</code>.
     */
    public void setIsbn13(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.BOOK.ISBN13</code>.
     */
    public String getIsbn13() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Book.BOOK.BOOKID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Book.BOOK.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Book.BOOK.SUBTITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Book.BOOK.ISBN10;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Book.BOOK.ISBN13;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getBookid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getSubtitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getIsbn10();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getIsbn13();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getBookid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getSubtitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getIsbn10();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getIsbn13();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookRecord value1(Integer value) {
        setBookid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookRecord value2(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookRecord value3(String value) {
        setSubtitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookRecord value4(String value) {
        setIsbn10(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookRecord value5(String value) {
        setIsbn13(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookRecord values(Integer value1, String value2, String value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BookRecord
     */
    public BookRecord() {
        super(Book.BOOK);
    }

    /**
     * Create a detached, initialised BookRecord
     */
    public BookRecord(Integer bookid, String title, String subtitle, String isbn10, String isbn13) {
        super(Book.BOOK);

        set(0, bookid);
        set(1, title);
        set(2, subtitle);
        set(3, isbn10);
        set(4, isbn13);
    }
}