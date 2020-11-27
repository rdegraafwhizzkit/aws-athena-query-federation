package nl.whizzkit;

import com.amazonaws.connectors.athena.jdbc.connection.DatabaseConnectionConfig;
import com.amazonaws.connectors.athena.jdbc.connection.GenericJdbcConnectionFactory;
import com.amazonaws.connectors.athena.jdbc.connection.JdbcConnectionFactory;
import com.amazonaws.connectors.athena.jdbc.manager.JdbcSplitQueryBuilder;
import com.amazonaws.connectors.athena.jdbc.postgresql.PostGreSqlQueryStringBuilder;
import com.amazonaws.connectors.athena.jdbc.postgresql.PostGreSqlRecordHandler;
import com.amazonaws.connectors.athena.jdbc.sqlserver.SQLServerQueryStringBuilder;
import com.amazonaws.connectors.athena.jdbc.sqlserver.SQLServerRecordHandler;
import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.athena.AmazonAthenaClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.HashMap;

public class X {
    public static void main(String[] args) throws Exception {

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().build();
        AWSSecretsManager secretsManager = AWSSecretsManagerClientBuilder.standard().build();
        AmazonAthena athena = AmazonAthenaClientBuilder.standard().build();
//        Connection connection = Mockito.mock(Connection.class);
        JdbcSplitQueryBuilder jdbcSplitQueryBuilder = new SQLServerQueryStringBuilder("\"");

        DatabaseConnectionConfig databaseConnectionConfig = new DatabaseConnectionConfig(
                "master",
                JdbcConnectionFactory.DatabaseEngine.SQLSERVER,
                "jdbc:sqlserver://localhost:1433;user=sa;password=dbo;"
        );

        JdbcConnectionFactory jdbcConnectionFactory = new GenericJdbcConnectionFactory(
                databaseConnectionConfig,
                new HashMap<String,String>()
        );

        SQLServerRecordHandler sqlServerRecordHandler = new SQLServerRecordHandler(
                databaseConnectionConfig,
                amazonS3,
                secretsManager,
                athena,
                jdbcConnectionFactory,
                jdbcSplitQueryBuilder
        );

        System.out.println();

    }
}
