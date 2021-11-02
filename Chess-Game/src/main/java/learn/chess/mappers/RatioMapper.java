package learn.chess.mappers;

import learn.chess.model.Match;
import learn.chess.model.Ratio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatioMapper implements RowMapper<Ratio> {
    @Override
    public Ratio mapRow(ResultSet resultSet, int i) throws SQLException {
        Ratio ratio = new Ratio();
        ratio.setRatioId(resultSet.getInt("ratios_id"));
        ratio.setWinRatio(resultSet.getInt("ratios_wins"));
        ratio.setLossRatio(resultSet.getInt("ratios_loss"));
        ratio.setTieRatio(resultSet.getInt("ratios_ties"));
        return ratio;
    }
}
