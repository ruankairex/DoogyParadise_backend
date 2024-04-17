package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmail(String email);
    Employee findByEmployeeId(Integer employeeId);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.tweetReports WHERE e.employeeId = ?1")
    Employee findEmployeeAndReportsByEmployeeId(Integer employeeId);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.tweetReports t WHERE t.reportsId = ?1")
    Employee findEmployeeByTweetReportId(Integer tweetReportId);
}
