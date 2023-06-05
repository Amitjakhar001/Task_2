// Enum for mode identifier
enum ModeIdentifier {
    DUMP,
    PASSTHROUGH,
    VALIDATE
}

// Enum for database identifier
enum DatabaseIdentifier {
    POSTGRES,
    REDIS,
    ELASTIC
}

// DataPoint class representing raw and processed data
class DataPoint {
    private String rawData;
    private String processedData;

    public DataPoint(String rawData) {
        this.rawData = rawData;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getProcessedData() {
        return processedData;
    }

    public void setProcessedData(String processedData) {
        this.processedData = processedData;
    }
}
// Database interface with common methods for different databases
interface Database {
    void connect();

    void insert(DataPoint dataPoint);

    void validate(DataPoint dataPoint);
}

// Implementation of PostgresDatabase
class PostgresDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connecting to Postgres database...");
    }

    @Override
    public void insert(DataPoint dataPoint) {
        System.out.println("Inserting dataPoint into Postgres database...");
    }

    @Override
    public void validate(DataPoint dataPoint) {
        System.out.println("Validating dataPoint against Postgres database...");
    }
}

// Implementation of RedisDatabase
class RedisDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connecting to Redis database...");
    }

    @Override
    public void insert(DataPoint dataPoint) {
        System.out.println("Inserting dataPoint into Redis database...");
    }

    @Override
    public void validate(DataPoint dataPoint) {
        System.out.println("Validating dataPoint against Redis database...");
    }
}

// Implementation of ElasticDatabase
class ElasticDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connecting to Elastic database...");
    }

    @Override
    public void insert(DataPoint dataPoint) {
        System.out.println("Inserting dataPoint into Elastic database...");
    }

    @Override
    public void validate(DataPoint dataPoint) {
        System.out.println("Validating dataPoint against Elastic database...");
    }
}

// DataProcessor class for processing data based on mode and database
class DataProcessor {
    private ModeIdentifier modeIdentifier;
    private DatabaseIdentifier databaseIdentifier;
    private Database database;

    public void configure(ModeIdentifier modeIdentifier, DatabaseIdentifier databaseIdentifier) {
        this.modeIdentifier = modeIdentifier;
        this.databaseIdentifier = databaseIdentifier;

        // Create database instance based on databaseIdentifier
        switch (databaseIdentifier) {
            case POSTGRES:
                this.database = new PostgresDatabase();
                break;
            case REDIS:
                this.database = new RedisDatabase();
                break;
            case ELASTIC:
                this.database = new ElasticDatabase();
                break;
        }

        // Connect to the selected database
        this.database.connect();
    }

    public void process(DataPoint dataPoint) {
        switch (modeIdentifier) {
            case DUMP:
                System.out.println("DataPoint dropped.");
                break;
            case PASSTHROUGH:
                this.database.insert(dataPoint);
                System.out.println("DataPoint inserted into the selected database.");
                break;
            case VALIDATE:
                this.database.validate(dataPoint);
                this.database.insert(dataPoint);
                System.out.println("DataPoint validated and inserted into the selected database.");
                break;
        }
    }
}

// Main class to demonstrate the usage
public class Main {
    public static void main(String[] args) {
        // Create instances of DataProcessor and configure it
        DataProcessor processor = new DataProcessor();
        processor.configure(ModeIdentifier.PASSTHROUGH, DatabaseIdentifier.POSTGRES);

        // Create a sample DataPoint
        DataPoint dataPoint = new DataPoint("Sample raw data");

        // Process the DataPoint
        processor.process(dataPoint);
    }
}

