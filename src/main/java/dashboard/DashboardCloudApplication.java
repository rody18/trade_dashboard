package dashboard;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dashboard.services.TdaService;

@SpringBootApplication(scanBasePackages = { "traderapi", "dashboard"} )
public class DashboardCloudApplication  implements CommandLineRunner  {

	@Autowired
    private ApplicationContext applicationContext;
	
	public static void main(String[] args) {
//		SpringApplication application = new SpringApplication(DashboardCloudApplication.class);
//        Properties properties = new Properties();
//        properties.put("server.port", 9999);
//        SpringApplication.setDefaultProperties(props());
//		SpringApplication.run(DashboardCloudApplication.class, args);
		
		new SpringApplicationBuilder(DashboardCloudApplication.class)
        .properties(props())
        .build()
        .run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		TdaService tdaService = applicationContext.getBean(TdaService.class);
		tdaService.showPriceTest();
	}

	private static Properties props() {
		Properties properties = new Properties();
		properties.setProperty("tda.token.refresh", "IT WORKS");
		return properties;
	}
}
