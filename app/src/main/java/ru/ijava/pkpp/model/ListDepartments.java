package ru.ijava.pkpp.model;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by rele on 5/21/17.
 */
public class ListDepartments {
    private TreeMap<Integer, Department> departments;

    public ListDepartments()
    {
        departments = new TreeMap<Integer, Department>();
    }

    public void addDepartment(Department d)
    {
        departments.put(d.getId(), d);
    }

    public TreeMap<Integer, Department> getDepartments()
    {
        return this.departments;
    }

    public ArrayList<Department> getDepartmentsList()
    {
        return new ArrayList<Department>(this.departments.values());
    }

    public ArrayList<Department> getChilds(int id)
    {
        ArrayList<Department> departments = new ArrayList<Department>();

        for (Department department : this.departments.values()) {
            if(id == department.getParent())
            {
                departments.add(department);
            }
        }

        if(departments.size() > 0)
        {
            return departments;
        }
        else {
            return null;
        }
    }

    public ArrayList<Department> getChilds(Department parentDepartment)
    {
        ArrayList<Department> departments = new ArrayList<Department>();

        for (Department department : this.departments.values()) {
            if(parentDepartment.getId() == department.getParent())
            {
                departments.add(department);
            }
        }

        if(departments.size() > 0)
        {
            return departments;
        }
        else {
            return null;
        }
    }
}
