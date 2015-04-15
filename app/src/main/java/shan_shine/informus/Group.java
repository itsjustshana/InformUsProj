package shan_shine.informus;

import java.util.Date;

/**
 * Created by Shanakay on 4/12/2015.
 */
public class Group {

    private String creator;
    private String groupName;
    private String grouDescr;
    private Date dateCreated;


    public Group(String creator, String groupName, String groupDescr, Date dateCreated) {
        super();
        this.creator = creator;
        this.groupName = groupName;
        this.grouDescr = groupDescr;
        this.dateCreated = dateCreated;
    }

}
