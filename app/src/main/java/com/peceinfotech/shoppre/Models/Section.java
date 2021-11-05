package com.peceinfotech.shoppre.Models;

import java.util.List;

public class Section {
    String sectionName ;
    int personal , additional;
    List<SectionItem> list;

    public Section(String sectionName, int personal, int additional, List<SectionItem> list) {
        this.sectionName = sectionName;
        this.personal = personal;
        this.additional = additional;
        this.list = list;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getPersonal() {
        return personal;
    }

    public void setPersonal(int personal) {
        this.personal = personal;
    }

    public int getAdditional() {
        return additional;
    }

    public void setAdditional(int additional) {
        this.additional = additional;
    }

    public List<SectionItem> getList() {
        return list;
    }

    public void setList(List<SectionItem> list) {
        this.list = list;
    }
}
