Problem Statement

Modern cloud platforms (AWS, Azure, GCP) allow users to configure servers with many optional 
features such as GPU, auto-scaling, backups, monitoring, and load balancers.
    
Design a Cloud Server Configuration System that:
Functional Requirements
Supports mandatory parameters
Region
CPU cores
RAM size
Operating system
Supports multiple optional configurations
GPU (with type)
Auto-scaling (min/max instances)
Load balancer
Backup (with frequency)
Security groups
Monitoring

Ensures immutability once the server is created
Performs centralized validation before object creation
Prevents creation of invalid server configurations
    
Design Constraints
Object creation must be readable and flexible
Constructor explosion must be avoided
Client code should be simple and expressive

UML design
+----------------------+
|     CloudServer      |
+----------------------+
| - region             |
| - cpuCores           |
| - ramGB              |
| - osType             |
| - gpuEnable          |
| - gpuType            |
| - autoScalling       |
| - minInstance        |
| - maxInstance        |
| - loadBalancer       |
| - backupEnabled      |
| - backupFrequency    |
| - securityGroups     |
| - monitoringEnabled  |
+----------------------+
| + getters()          |
| + toString()         |
+----------^-----------+
           |
           |
+----------------------+
|    CloudBuilder      |
+----------------------+
| - region             |
| - cpuCores           |
| - ramGB              |
| - osType             |
| - optional fields    |
+----------------------+
| + setX()             |
| + build()            |
+----------------------+


// Represents an immutable Cloud Server configuration
class CloudServer {

    // Mandatory fields
    private final String region;
    private final int cpuCores;
    private final int ramGB;
    private final String osType;

    // Optional configuration fields
    private final boolean gpuEnable;
    private final String gpuType;

    private final boolean autoScalling;
    private final int minInstance;
    private final int maxInstance;

    private final boolean loadBalancerEnabled;

    private final boolean backupEnabled;
    private final int backupFrequency;

    private final String securityGroups;
    private final boolean monitoringEnabled;

    // Private constructor that accepts Builder instance
    // Ensures object creation only via Builder
    CloudServer(CloudBuilder builder) {
        this.region = builder.region;
        this.cpuCores = builder.cpuCores;
        this.ramGB = builder.ramGB;
        this.osType = builder.osType;
        this.gpuEnable = builder.gpuEnable;
        this.gpuType = builder.gpuType;
        this.autoScalling = builder.autoScalling;
        this.minInstance = builder.minInstance;
        this.maxInstance = builder.maxInstance;
        this.loadBalancerEnabled = builder.loadBalancerEnabled;
        this.backupEnabled = builder.backupEnabled;
        this.backupFrequency = builder.backupFrequency;
        this.securityGroups = builder.securityGroups;
        this.monitoringEnabled = builder.monitoringEnabled;
    }

    // Getter methods (no setters → immutability)
    public String getRegion() {
        return region;
    }

    public int getCPUCores() {
        return cpuCores;
    }

    public int getRAMGB() {
        return ramGB;
    }

    public String getOSTYPE() {
        return osType;
    }

    public boolean getGPUEnabled() {
        return gpuEnable;
    }

    public String getGPUType() {
        return gpuType;
    }

    public boolean getAutoScalling() {
        return autoScalling;
    }

    public int getMinInstance() {
        return minInstance;
    }

    public int getMaxInstance() {
        return maxInstance;
    }

    public boolean getLoadBalancerEnabled() {
        return loadBalancerEnabled;
    }

    public boolean getBackupEnablled() {
        return backupEnabled;
    }

    public int getBackupFrequency() {
        return backupFrequency;
    }

    public String getSecurityGroups() {
        return securityGroups;
    }

    public boolean GetMonitoringEnablled() {
        return monitoringEnabled;
    }

    // Overriding toString() for easy debugging and logging
    @Override
    public String toString() {
        return "CloudServer{" +
                "region='" + region + '\'' +
                ", cpuCores=" + cpuCores +
                ", ramGB=" + ramGB +
                ", osType='" + osType + '\'' +
                ", gpuEnable=" + gpuEnable +
                ", gpuType='" + gpuType + '\'' +
                ", autoScalling=" + autoScalling +
                ", minInstance=" + minInstance +
                ", maxInstance=" + maxInstance +
                ", loadBalancerEnabled=" + loadBalancerEnabled +
                ", backupEnabled=" + backupEnabled +
                ", backupFrequency=" + backupFrequency +
                ", securityGroups='" + securityGroups + '\'' +
                ", monitoringEnabled=" + monitoringEnabled +
                '}';
    }

    // ---------------- BUILDER CLASS ----------------
    public static class CloudBuilder {

        // Mandatory fields (required to create a server)
        private final String region;
        private final int cpuCores;
        private final int ramGB;
        private final String osType;

        // Optional fields with default values
        private boolean gpuEnable;
        private String gpuType;

        private boolean autoScalling;
        private int minInstance = 1;
        private int maxInstance = 1;

        private boolean loadBalancerEnabled;

        private boolean backupEnabled;
        private int backupFrequency;

        private String securityGroups;
        private boolean monitoringEnabled;

        // Constructor enforces mandatory fields
        public CloudBuilder(String region, int cpuCores, int ramGB, String osType) {

            // Validation of required parameters
            if (region == null || region.isEmpty()) {
                throw new IllegalArgumentException("Invalid region");
            }
            if (cpuCores <= 0) {
                throw new IllegalArgumentException("Invalid CPU core numbers");
            }
            if (ramGB <= 0) {
                throw new IllegalArgumentException("Invalid RAM");
            }
            if (osType == null || osType.isEmpty()) {
                throw new IllegalArgumentException("OS type is Invalid");
            }

            this.region = region;
            this.cpuCores = cpuCores;
            this.ramGB = ramGB;
            this.osType = osType;
        }

        // Fluent setter methods for optional configuration
        public CloudBuilder setGPUEnabled(boolean gpuEnable) {
            this.gpuEnable = gpuEnable;
            return this;
        }

        public CloudBuilder setGPUType(String gpuType) {
            this.gpuType = gpuType;
            return this;
        }

        public CloudBuilder setAutoScalling(boolean autoScalling) {
            this.autoScalling = autoScalling;
            return this;
        }

        public CloudBuilder setMinInstance(int minInstance) {
            this.minInstance = minInstance;
            return this;
        }

        public CloudBuilder setMaxInstance(int maxInstance) {
            this.maxInstance = maxInstance;
            return this;
        }

        public CloudBuilder setLoadBalancer(boolean loadBalancerEnabled) {
            this.loadBalancerEnabled = loadBalancerEnabled;
            return this;
        }

        public CloudBuilder setBackupEnable(boolean backupEnabled) {
            this.backupEnabled = backupEnabled;
            return this;
        }

        public CloudBuilder setBackFrequency(int backupFrequency) {
            this.backupFrequency = backupFrequency;
            return this;
        }

        public CloudBuilder setSecurityGroups(String securityGroups) {
            this.securityGroups = securityGroups;
            return this;
        }

        public CloudBuilder setMonitoringEnabled(boolean monitoringEnabled) {
            this.monitoringEnabled = monitoringEnabled;
            return this;
        }

        // Centralized validation before object creation
        public CloudServer build() {

            // GPU constraint validation
            if (gpuEnable && (gpuType == null || gpuType.isEmpty())) {
                throw new IllegalArgumentException(
                        "GPU type must be specified when GPU is enabled");
            }

            // Auto-scaling validation
            if (autoScalling) {
                if (minInstance < 1 || maxInstance < minInstance) {
                    throw new IllegalArgumentException(
                            "Auto Scalling can't be implemented");
                }
            }

            // Backup validation
            if (backupEnabled && backupFrequency <= 0) {
                throw new IllegalArgumentException(
                        "Backup Frequency should be greater than 0");
            }

            // Create immutable CloudServer object
            return new CloudServer(this);
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class Main {
    public static void main(String[] args) {

        // ✅ Valid CloudServer configuration using Builder
        CloudServer server = new CloudServer.CloudBuilder(
                "ap-south-1",   // region
                8,              // CPU cores
                32,             // RAM in GB
                "Linux"         // OS type
        )
        .setGPUEnabled(true)
        .setGPUType("NVIDIA A100")
        .setAutoScalling(true)
        .setMinInstance(2)
        .setMaxInstance(10)
        .setLoadBalancer(true)
        .setBackupEnable(true)
        .setBackFrequency(24)           // Backup every 24 hours
        .setSecurityGroups("ssh,http,https")
        .setMonitoringEnabled(true)
        .build();

        // Print final server configuration
        System.out.println(server);

        // ❌ Invalid configuration example (GPU enabled but type missing)
        try {
            CloudServer invalidServer = new CloudServer.CloudBuilder(
                    "us-east-1",
                    4,
                    16,
                    "Ubuntu"
            )
            .setGPUEnabled(true)   // GPU enabled
            // GPU type not set → validation failure
            .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating server: " + e.getMessage());
        }
    }
}
