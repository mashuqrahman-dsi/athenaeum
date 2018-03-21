/*
 * This file is generated by jOOQ.
*/
package com.mashuq.athenaeum.domain.athenaeum.tables;


import com.mashuq.athenaeum.domain.athenaeum.Athenaeum;
import com.mashuq.athenaeum.domain.athenaeum.Indexes;
import com.mashuq.athenaeum.domain.athenaeum.Keys;
import com.mashuq.athenaeum.domain.athenaeum.tables.records.BookinformationrequestRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class Bookinformationrequest extends TableImpl<BookinformationrequestRecord> {

    private static final long serialVersionUID = -1160554282;

    /**
     * The reference instance of <code>ATHENAEUM.BOOKINFORMATIONREQUEST</code>
     */
    public static final Bookinformationrequest BOOKINFORMATIONREQUEST = new Bookinformationrequest();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BookinformationrequestRecord> getRecordType() {
        return BookinformationrequestRecord.class;
    }

    /**
     * The column <code>ATHENAEUM.BOOKINFORMATIONREQUEST.BOOKREQUESTID</code>.
     */
    public final TableField<BookinformationrequestRecord, Integer> BOOKREQUESTID = createField("BOOKREQUESTID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>ATHENAEUM.BOOKINFORMATIONREQUEST.BOOKID</code>.
     */
    public final TableField<BookinformationrequestRecord, Integer> BOOKID = createField("BOOKID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>ATHENAEUM.BOOKINFORMATIONREQUEST.REQUESTSTATUS</code>.
     */
    public final TableField<BookinformationrequestRecord, Integer> REQUESTSTATUS = createField("REQUESTSTATUS", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>ATHENAEUM.BOOKINFORMATIONREQUEST.REQUESTEREMAIL</code>.
     */
    public final TableField<BookinformationrequestRecord, String> REQUESTEREMAIL = createField("REQUESTEREMAIL", org.jooq.impl.SQLDataType.VARCHAR(128), this, "");

    /**
     * Create a <code>ATHENAEUM.BOOKINFORMATIONREQUEST</code> table reference
     */
    public Bookinformationrequest() {
        this(DSL.name("BOOKINFORMATIONREQUEST"), null);
    }

    /**
     * Create an aliased <code>ATHENAEUM.BOOKINFORMATIONREQUEST</code> table reference
     */
    public Bookinformationrequest(String alias) {
        this(DSL.name(alias), BOOKINFORMATIONREQUEST);
    }

    /**
     * Create an aliased <code>ATHENAEUM.BOOKINFORMATIONREQUEST</code> table reference
     */
    public Bookinformationrequest(Name alias) {
        this(alias, BOOKINFORMATIONREQUEST);
    }

    private Bookinformationrequest(Name alias, Table<BookinformationrequestRecord> aliased) {
        this(alias, aliased, null);
    }

    private Bookinformationrequest(Name alias, Table<BookinformationrequestRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Athenaeum.ATHENAEUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.CONSTRAINT_INDEX_B);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<BookinformationrequestRecord, Integer> getIdentity() {
        return Keys.IDENTITY_BOOKINFORMATIONREQUEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<BookinformationrequestRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<BookinformationrequestRecord, ?>>asList(Keys.CONSTRAINT_B);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bookinformationrequest as(String alias) {
        return new Bookinformationrequest(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bookinformationrequest as(Name alias) {
        return new Bookinformationrequest(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Bookinformationrequest rename(String name) {
        return new Bookinformationrequest(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Bookinformationrequest rename(Name name) {
        return new Bookinformationrequest(name, null);
    }
}
