package com.skhynix.datahub.striimtqlparser2.batch.backup;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.Employee;
import com.skhynix.datahub.striimtqlparser2.common.Constants;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
public class CustomDataSourceReader extends JdbcCursorItemReader<Employee> implements ItemReader<Employee>{

    public CustomDataSourceReader(@Qualifier(Constants.CatalogDataSource) @Autowired DataSource dataSource) {
        setDataSource(dataSource);
        setSql("SELECT id, name, salary FROM employee");
        setFetchSize(100);
        setRowMapper(new EmployeeRowMapper());
    }

    public class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee  = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setSalary(rs.getInt("salary"));
            return employee;
        }
    }
}