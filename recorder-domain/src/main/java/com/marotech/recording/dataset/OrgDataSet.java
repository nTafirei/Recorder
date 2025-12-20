package com.marotech.recording.dataset;

import com.github.javafaker.Faker;
import com.marotech.recording.config.Config;
import com.marotech.recording.model.*;
import com.marotech.recording.repository.jdbc.GenericJDBCRepository;
import com.marotech.recording.service.RepositoryService;
import com.marotech.recording.util.Constants;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@Transactional
@Component
@DependsOn("baseDataSet")
public class OrgDataSet {
    private boolean isProd = false;

    @PostConstruct
    public void create() {
        try {
            String env = config.getProperty("env.deployment");
            if ("prod".equalsIgnoreCase(env)) {
                isProd = true;
            }
            String ext = "uk";

            long num = repositoryService.countAuthUsers();
            if (num > 0) {
                return;
            }
            String adminEmail = "paystream_system_admin@paystream.co." + ext;
            createAdminUser(adminEmail);

            if (!isProd) {
                createOrgUsers();
            }
        } catch (Exception e) {
            LOG.error("Error building basic data :", e);
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void createAdminUser(String adminEmail) throws Exception {

        AuthUser authUser = new AuthUser();
        authUser.setUserName("0712374658");
        authUser.setMobileNumber("0712374658");
        String newPassword = AuthUser.encodedPassword("test");
        authUser.setPassword(newPassword);
        createSecurityQuestions(authUser);

        repository.save(authUser);
        User adminUser = new User();
        adminUser.setNationalId("12345001");

        adminUser.setFirstName("System Admin");
        adminUser.setLastName("System Admin");
        adminUser.setMiddleName("System Admin");
        adminUser.setEmail(adminEmail);

        Address address = new Address();
        address.setCountry(config.getProperty("country"));
        address.setAddress("80 Samora Machel Avenue");
        address.setCity("Harare");
        repositoryService.save(address);
        adminUser.setAddress(address);
        adminUser.setNationalId("0712374658");
        adminUser.setMobileNumber("0712374658");
        adminUser.setDateOfBirth(LocalDate.now().minusYears(40));
        repository.save(adminUser);

        Iterable<UserRole> roles = repositoryService.fetchAllRoles();

        for (UserRole role : roles) {
            if (role.getRoleName().equals(Constants.SYS_ADMIN)
                    || role.getRoleName().equals(Constants.ADMIN)) {
                adminUser.addUserRole(role);
                break;
            }
        }

        repository.save(adminUser);
        authUser.setUser(adminUser);
        repository.save(authUser);
    }

    private void createSecurityQuestions(AuthUser authUser) {
        SecurityQuestion question = new SecurityQuestion();
        question.setTag(SecurityQuestionTag.ONE);
        question.setQuestion(config.getProperty("securityQuestion1"));
        question.setAnswer("test");
        repositoryService.save(question);
        authUser.getSecurityQuestions().add(question);

        question = new SecurityQuestion();
        question.setTag(SecurityQuestionTag.TWO);
        question.setQuestion(config.getProperty("securityQuestion2"));
        question.setAnswer("test");
        repositoryService.save(question);
        authUser.getSecurityQuestions().add(question);

        question = new SecurityQuestion();
        question.setTag(SecurityQuestionTag.THREE);
        question.setQuestion(config.getProperty("securityQuestion3"));
        question.setAnswer("test");
        repositoryService.save(question);
        authUser.getSecurityQuestions().add(question);
    }

    private void createOrgUsers() throws Exception {

        if (isProd) {
            return;
        }

        String country = config.getProperty("country");
        String ext = config.getProperty(country + ".extension");
        String domain = config.getProperty("domain");
        Iterable<UserRole> roles = repositoryService.fetchAllRoles();
        Faker faker;
        String mobileNumber = "263 71 793 113";
        for (int i = 0; i < 5; i++) {
            faker = new Faker();
            String lastname = faker.name().lastName();
            User user = repositoryService.fetchUserByEmail(lastname + domain + ext);

            if (user == null) {
                AuthUser authUser = new AuthUser();
                authUser.setMobileNumber(mobileNumber + i);
                authUser.setUserName(lastname.toLowerCase());
                String newPassword = AuthUser.encodedPassword("test");
                authUser.setPassword(newPassword);
                createSecurityQuestions(authUser);
                repository.save(authUser);
                user = new User();
                user.setActiveStatus(ActiveStatus.NOT_ACTIVE);
                user.setDateOfBirth(LocalDate.now().minusYears(20));
                user.setFirstName(faker.name().firstName());
                user.setLastName(lastname);
                user.setNationalId(faker.idNumber().ssnValid());
                user.setEmail(lastname.toLowerCase() + domain + ext);

                Address address = new Address();
                address.setCountry(country);
                address.setAddress("80 Samora Machel Avenue");
                address.setCity("Harare");
                repositoryService.save(address);
                user.setAddress(address);

                user.setMobileNumber(faker.phoneNumber().phoneNumber());
                for (UserRole role : roles) {
                    if (role.getRoleName().equals("User")) {
                        user.addUserRole(role);
                    }
                }

                repository.save(user);
                authUser.setUser(user);
                repository.save(authUser);
            }
        }
    }

    private static final Random RANDOM = new Random();

    @Autowired
    private GenericJDBCRepository repository;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private Config config;
    private static final Logger LOG = LoggerFactory.getLogger(OrgDataSet.class);
}
