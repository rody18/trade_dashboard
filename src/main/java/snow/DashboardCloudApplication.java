package snow;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"snow.traderapi", "snow.dashboard"})
public class DashboardCloudApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DashboardCloudApplication.class)
                .build()
                .run(args);
    }
}
