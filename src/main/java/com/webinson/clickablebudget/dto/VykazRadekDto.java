package com.webinson.clickablebudget.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
@Data
@NoArgsConstructor
@XmlRootElement(name = "VykazRadek")
@XmlAccessorType(XmlAccessType.FIELD)
public class VykazRadekDto {

    long id;
    private String name;

    @XmlElement(name = "Paragraf")
    private Integer paragraf;

    @XmlElement(name = "Polozka")
    private String polozka;

    @XmlElement(name = "RozpocetSchvaleny")
    private Double approvedBudget;

    @XmlElement(name = "RozpocetPoZmenach")
    private Double adjustedBudget;

    @XmlElement(name = "Vysledek")
    private Double spentBudget;

    private String state;

    @XmlElement(name = "DatumVykaz")
    private Date date;

    private String getStringApprovedBudget;
    private String getStringAdjustedBudget;
    private String getStringSpentBudget;
    private List<VykazRadekDto> children;
    private VykazRadekDto parent;

    private String levelColor;

    /*public VykazRadekDto(VykazRadekDto parent) {
        this.parent = parent;
    }*/

    public VykazRadekDto(VykazRadekDto another) {
        this.name = another.name;
        this.paragraf = another.paragraf;
        this.polozka = another.polozka;
        this.approvedBudget = another.approvedBudget;
        this.adjustedBudget = another.adjustedBudget;
        this.spentBudget = another.spentBudget;
        this.state = another.state;
        this.date = another.date;
        this.getStringApprovedBudget = another.getStringApprovedBudget;
        this.getStringAdjustedBudget = another.getStringAdjustedBudget;
        this.getStringSpentBudget = another.getStringSpentBudget;
        this.children = another.children;
        this.parent = another.parent;
    }

    public VykazRadekDto(String name, Double approvedBudget, Double adjustedBudget, Double spentBudget) {
        this.name = name;
        this.approvedBudget = approvedBudget;
        this.adjustedBudget = adjustedBudget;
        this.spentBudget = spentBudget;
    }

    public VykazRadekDto(Double approvedBudget) {
        this.approvedBudget = approvedBudget;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((approvedBudget == null) ? 0 : approvedBudget.hashCode());
        result = prime * result + ((adjustedBudget == null) ? 0 : adjustedBudget.hashCode());
        result = prime * result + ((spentBudget == null) ? 0 : spentBudget.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VykazRadekDto other = (VykazRadekDto) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (approvedBudget == null) {
            if (other.approvedBudget != null)
                return false;
        } else if (!approvedBudget.equals(other.approvedBudget))
            return false;
        if (adjustedBudget == null) {
            if (other.adjustedBudget != null)
                return false;
        } else if (!adjustedBudget.equals(other.adjustedBudget))
            return false;
        if (spentBudget == null) {
            if (other.spentBudget != null)
                return false;
        } else if (!spentBudget.equals(other.spentBudget))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(VykazRadekDto vykazRadekDto) {
        return this.getName().compareTo(vykazRadekDto.getName());
    }

    public String getStringApprovedBudget() {

        String pattern = "###,###.### Kč";
        DecimalFormat df = new DecimalFormat(pattern);
        Double aBudget = new Double(this.approvedBudget);
        return df.format(this.approvedBudget);

    }

    public String getStringAdjustedBudget() {

        String pattern = "###,### Kč";
        DecimalFormat df = new DecimalFormat(pattern);
        Double aBudget = new Double(this.adjustedBudget);
        return df.format(this.adjustedBudget);

    }

    public String getStringSpentBudget() {

        String pattern = "###,### Kč";
        DecimalFormat df = new DecimalFormat(pattern);
        Double aBudget = new Double(this.spentBudget);
        return df.format(this.spentBudget);

    }

    public String getState() {

        String result;

        Double aBudget = new Double(this.adjustedBudget);
        Double sBudget = new Double(this.spentBudget);

        Integer a = aBudget.intValue();
        Integer s = sBudget.intValue();

        Double state = (sBudget / aBudget) * 100;
        Integer st = (int) Math.round(state);

        result = st.toString();

        if (st < 0) {
            result = "100";
        }


        return result + " %";

    }

    public Double getApprovedBudget() {
        return this.approvedBudget;
    }

    public void setChildren(List<VykazRadekDto> children) {
        if (children != null) {
            this.children = children;
        }
    }

    public void setChildren(VykazRadekDto children) {
        this.children.add(children);
    }

    private static VykazRadekDto addChild(VykazRadekDto parent, long id) {
        VykazRadekDto node = new VykazRadekDto(parent);
        node.setId(id);
        parent.getChildren().add(node);

        return node;
    }

}
