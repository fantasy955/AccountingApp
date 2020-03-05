package com.experiment.lenovo.accountingsoftware.database.bill;

import android.provider.BaseColumns;

public interface BillColumns extends BaseColumns
{
    String BILLTYPE = "billtype";
    String BILLCLASS = "billclass";
    String BILLVALUE = "billvalue";
    String NOTE = "note";
    String DATEOFBILL = "dateofbill";
    String _TIMEOFADDING = "_timeofadding";
    String _BID = "_bid";
    String UID = "uid";
}
