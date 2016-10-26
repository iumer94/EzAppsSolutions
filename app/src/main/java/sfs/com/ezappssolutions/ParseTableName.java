package sfs.com.ezappssolutions;

/**
 * Created by Umer Ejaz on 10/25/2016.
 */

public class ParseTableName {
    public String tableName;
    public String setParseTableName(String titleStr) {
        if (titleStr.equals("Sole Propretior")) {
            this.tableName = "SolePropreitor";
        } else if (titleStr.equals("LLC")) {
            this.tableName = "LLC";
        } else if (titleStr.equals("Trust")) {
            this.tableName = "Trust";
        } else if (titleStr.equals("Estate Of Deceased Ind")) {
            this.tableName = "EstateDeceased";
        } else if (titleStr.equals("Non Profit")) {
            this.tableName = "NonProfit";
        } else if (titleStr.equals("Partnership")) {
            this.tableName = "Partnership";
        } else if (titleStr.equals("Corporation")) {
            this.tableName = "Corporation";
        } else if (titleStr.equals("S-Corporation")) {
            this.tableName = "SCorporation";
        } else if (titleStr.equals("Personal Service Corporation")) {
            this.tableName = "PersServiceCorporation";
        } else if (titleStr.equals("Church Controlled Org.")) {
            this.tableName = "ChurchControlled";
        } else {
            this.tableName = "NotMatching";
        }
        return titleStr;
    }

    public String getParseTableName()
    {
        return this.tableName;
    }


}
