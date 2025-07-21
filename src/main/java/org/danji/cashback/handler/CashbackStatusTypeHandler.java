package org.danji.cashback.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.danji.cashback.enums.CashBackStatus;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(CashBackStatus.class)
public class CashbackStatusTypeHandler extends BaseTypeHandler<CashBackStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CashBackStatus status, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, status.name()); // ENUM name 그대로 저장
    }

    @Override
    public CashBackStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String status = rs.getString(columnName);
        return status == null ? null : CashBackStatus.valueOf(status);
    }

    @Override
    public CashBackStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String status = rs.getString(columnIndex);
        return status == null ? null : CashBackStatus.valueOf(status);
    }

    @Override
    public CashBackStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String status = cs.getString(columnIndex);
        return status == null ? null : CashBackStatus.valueOf(status);
    }
}
