package com.marotech.recording.service;

import com.marotech.recording.config.Config;
import com.marotech.recording.model.*;
import com.marotech.recording.repository.jdbc.GenericJDBCRepository;
import com.marotech.recording.util.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RepositoryService {
    public static final String TAX_ACCOUNT = " Tax Account";
    public static final String SYSTEM_WALLET = " System Wallet";
    public static final String COMMISSIONS_ACCOUNT = " Commissions Account";
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private Config config;

    @Autowired
    private GenericJDBCRepository<BaseEntity> repository;

    public AuthUser fetchDemoUser() {
        List<AuthUser> users = entityManager.createQuery("SELECT u from AuthUser u", AuthUser.class).getResultList();
        for (AuthUser authUser : users) {
            if (!authUser.getUser().getIsAdmin()) {
                return authUser;
            }
        }
        return null;
    }

    //DO NOT DELETE!
    public AppSession fetchAppSessionByMobileNumberAndOTP(String mobileNumber, String otp) {
        try {
            return entityManager.createQuery(
                            "SELECT r FROM AppSession r WHERE r.mobileNumber =?1 and u.otp =?2", AppSession.class)
                    .setParameter(1, mobileNumber)
                    .setParameter(1, otp)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public long countRecordingsForDates(LocalDate fromDate, LocalDate toDate) {
        return entityManager.createQuery("SELECT count(id) from Recording u where " +
                        "u.dateCreated BETWEEN ?1 and ?2", Long.class).
                setParameter(1, fromDate.atStartOfDay()).
                setParameter(2, toDate.plusDays(1).atStartOfDay())
                .getSingleResult();
    }

    //---------------------------------------------------------------------
    public List<Recording> fetchRecordingsForDates(LocalDate fromDate, LocalDate toDate, Page page) {
        return entityManager.createQuery("SELECT u from Recording u where " +
                        "u.dateCreated BETWEEN ?1 and ?2 " +
                        "ORDER by dateCreated DESC", Recording.class).
                setParameter(1, fromDate.atStartOfDay()).
                setParameter(2, toDate.plusDays(1).atStartOfDay())
                .setFirstResult(page.getFirstResultIndex())
                .setMaxResults(page.getItemsPerPage())
                .getResultList();
    }

    public long countRecordings() {
        return entityManager.createQuery("SELECT count(id) from Recording u", Long.class)
                .getSingleResult();
    }

    public List<Recording> fetchRecordings(Page page) {
        return entityManager.createQuery("SELECT u from Recording u " +
                        "ORDER by dateCreated DESC", Recording.class)
                .setFirstResult(page.getFirstResultIndex())
                .setMaxResults(page.getItemsPerPage())
                .getResultList();
    }

    //---------------------------------------------------------------------
    public long countRecordingsForUser(User user) {
        return entityManager.createQuery("SELECT count(id) from Recording u where u.user.id =?1 ", Long.class).
                setParameter(1, user.getId())
                .getSingleResult();
    }

    public List<Recording> fetchRecordingsForUser(User user, LocalDate fromDate,
                                                  LocalDate toDate, Page page) {
        return entityManager.createQuery("SELECT u from Recording u where u.user.id =?1 " +
                        "AND u.dateCreated BETWEEN ?2 and ?3 " +
                        "ORDER by dateCreated DESC", Recording.class).
                setParameter(1, user.getId()).
                setParameter(2, fromDate.atStartOfDay()).
                setParameter(3, toDate.plusDays(1).atStartOfDay())
                .setFirstResult(page.getFirstResultIndex())
                .setMaxResults(page.getItemsPerPage())
                .getResultList();
    }

    public List<Recording> fetchRecordingsForUser(User user, Page page) {
        return entityManager.createQuery("SELECT u from Recording u where u.user.id =?1 " +
                        "ORDER by dateCreated DESC", Recording.class).
                setParameter(1, user.getId())
                .setFirstResult(page.getFirstResultIndex())
                .setMaxResults(page.getItemsPerPage())
                .getResultList();
    }

    public long countRecordingsForUser(User user, LocalDate fromDate,
                                       LocalDate toDate) {
        return entityManager.createQuery("SELECT count(id) from Recording u where u.user.id =?1 " +
                        "AND u.dateCreated BETWEEN ?2 and ?3", Long.class).
                setParameter(1, user.getId()).
                setParameter(2, fromDate.atStartOfDay()).
                setParameter(3, toDate.plusDays(1).atStartOfDay())
                .getSingleResult();
    }

    public AuthUser fetchDemoAdmin() {
        List<AuthUser> users = entityManager.createQuery("SELECT u from AuthUser u", AuthUser.class).getResultList();
        for (AuthUser authUser : users) {
            if (authUser.getUser().getIsAdmin()) {
                return authUser;
            }
        }
        return null;
    }

    public List<User> fetchAllUsersByRoleNames(List<String> roleNames) {
        String jpql = "SELECT DISTINCT u FROM User u JOIN u.userRoles r WHERE r.roleName IN :roleNames";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("roleNames", roleNames);
        return query.getResultList();
    }

    public List<User> fetchActiveUsersByRoleNames(List<String> roleNames) {
        String jpql = "SELECT DISTINCT u FROM User u JOIN u.userRoles r WHERE r.roleName IN :roleNames AND " +
                " u.activeStatus =:status";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("roleNames", roleNames);
        query.setParameter("status", ActiveStatus.ACTIVE);
        return query.getResultList();
    }

    public List<Recording> fetchRecordingsByUser(User user) {
        return entityManager.createQuery(
                        "SELECT r FROM Recording r WHERE r.user =?1", Recording.class)
                .setParameter(1, user)
                .getResultList();
    }

    public List<Recording> fetchByMobileNumber(String mobileNumber) {
        return entityManager.createQuery(
                        "SELECT r FROM FSM r WHERE r.user.mobileNumber =?1", Recording.class)
                .setParameter(1, mobileNumber)
                .getResultList();
    }

    public List<UserRole> fetchRolesNotInCollection(List<String> roleNames) {
        return entityManager.createQuery(
                        "SELECT r FROM UserRole r WHERE r.roleName NOT IN :roleNames", UserRole.class)
                .setParameter("roleNames", roleNames)
                .getResultList();
    }

    public AppSession fetchAppSessionByToken(String token) {
        try {
            return entityManager.createQuery(
                            "SELECT r FROM AppSession r WHERE r.token =?1", AppSession.class)
                    .setParameter(1, token)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Notification> fetchNotificationsByRecipient(User recipient) {
        if (recipient == null) {
            return new ArrayList<>();
        }
        String jpql = "SELECT t FROM Notification t WHERE t.recipient = :recipient ORDER BY dateCreated";

        TypedQuery<Notification> query = entityManager.createQuery(jpql, Notification.class);
        query.setParameter("recipient", recipient);
        return query.getResultList();
    }

    public User fetchUserByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return null;
        }
        try {
            return entityManager.createQuery("SELECT u from User u WHERE u.email =?1", User.class).
                    setParameter(1, email).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public LanguageModel fetchLanguageModelByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return entityManager.createQuery("SELECT u from LanguageModel u WHERE u.name =?1", LanguageModel.class).
                    setParameter(1, name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    public List<LanguageModel> fetchAllLanguageModels() {
        return entityManager.createQuery("SELECT u from LanguageModel u", LanguageModel.class).
                getResultList();
    }

    //---------------------------------------------------------------------


    public Activity fetchActivityById(String id) {
        try {
            return entityManager.createQuery("SELECT u from Activity u where u.id =?1", Activity.class).
                    setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Recording fetchRecordingById(String id) {
        try {
            return entityManager.createQuery("SELECT u from Recording u where u.id =?1", Recording.class).
                    setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Comment fetchCommentById(String id) {
        try {
            return entityManager.createQuery("SELECT u from Comment u where u.id =?1", Comment.class).
                    setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Attachment fetchAttachmentById(String id) {
        try {
            return entityManager.createQuery("SELECT u from Attachment u where u.id =?1", Attachment.class).
                    setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public LanguageModel fetchLanguageModelById(String id) {
        try {
            return entityManager.createQuery("SELECT u from LanguageModel u where u.id =?1", LanguageModel.class).
                    setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public JDBCDataSource fetchJDBCDataSourceById(String id) {
        try {
            return entityManager.createQuery("SELECT u from JDBCDataSource u where u.id =?1", JDBCDataSource.class).
                    setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User fetchUserById(String id) {
        try {
            return entityManager.createQuery("SELECT u from User u where u.id =?1", User.class).
                    setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public UserRole fetchUserRoleById(String id) {
        try {
            return entityManager.createQuery("SELECT u from UserRole u where u.id =?1", UserRole.class).
                    setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Notification fetchNotificationById(String id) {
        try {
            return entityManager.createQuery("SELECT u from Notification u where u.id =?1  ORDER BY u.dateCreated DESC", Notification.class).
                    setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public long countUsersByRoleAndActiveStatus(String roleName, ActiveStatus status) {

        String jpql = "SELECT count(u) FROM User u JOIN u.userRoles r WHERE r.roleName =:roleName" +
                " AND u.activeStatus =:status";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("roleName", roleName);
        query.setParameter("status", status);
        return query.getSingleResult();
    }

    public List<User> fetchUsersByRoleAndActiveStatus(String roleName, ActiveStatus status,
                                                      Page page) {

        String jpql = "SELECT u FROM User u JOIN u.userRoles r WHERE r.roleName =:roleName" +
                " AND u.activeStatus =:status ORDER BY u.lastName";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("roleName", roleName);
        query.setParameter("status", status).
                setFirstResult(page.getFirstResultIndex()).
                setMaxResults(page.getItemsPerPage());
        return query.getResultList();
    }

    public List<UserRole> fetchAllRoles() {
        return entityManager.createQuery("SELECT u from UserRole u ORDER BY u.roleName ASC", UserRole.class).getResultList();
    }

    public long countActivitiesByActivityType(ActivityType activityType,
                                              LocalDate startDate,
                                              LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();
        return entityManager.createQuery("SELECT count(u) from Activity u WHERE u.activityType =?1 " +
                                "AND u.dateCreated " +
                                "BETWEEN ?2 AND ?3",
                        Long.class)
                .setParameter(1, activityType)
                .setParameter(2, start)
                .setParameter(3, end)
                .getSingleResult();
    }

    public long countActivitiesByActivityType(ActivityType activityType) {
        try {
            return entityManager.createQuery("SELECT count(*) from Activity u WHERE u.activityType =?1",
                            Long.class)
                    .setParameter(1, activityType)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }


    public List<Activity> fetchActivitiesByActivityType(ActivityType activityType, Page page) {
        return entityManager.createQuery("SELECT u from Activity u WHERE u.activityType =?1 " +
                                " ORDER BY u.dateCreated",
                        Activity.class).setParameter(1, activityType)
                .setFirstResult(page.getFirstResultIndex())
                .setMaxResults(page.getItemsPerPage()).
                getResultList();
    }

    public List<Activity> fetchActivitiesByActivityType(ActivityType activityType,
                                                        LocalDate startDate,
                                                        LocalDate endDate, Page page) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();
        return entityManager.createQuery("SELECT u from Activity u WHERE u.activityType =?1 " +
                                "AND u.dateCreated " +
                                "BETWEEN ?2 AND ?3 ORDER BY u.dateCreated",
                        Activity.class).setParameter(1, activityType)
                .setParameter(2, start)
                .setParameter(3, end)
                .setFirstResult(page.getFirstResultIndex())
                .setMaxResults(page.getItemsPerPage())
                .getResultList();
    }


    public ResetInfo fetchResetInfoByToken(String token) {
        try {
            return entityManager.createQuery("SELECT u from ResetInfo u WHERE u.token =?1",
                    ResetInfo.class).setParameter(1, token).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User fetchUserByNationalId(String nationalId) {
        try {
            return entityManager.createQuery("SELECT u from User u WHERE u.nationalId =?1", User.class).
                    setParameter(1, nationalId).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User fetchUserByMobilePhone(String mobilePhone) {
        try {
            return entityManager.createQuery("SELECT u from User u WHERE u.mobileNumber =?1", User.class).
                    setParameter(1, mobilePhone).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public long countAuthUsers() {
        try {
            TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(u) FROM AuthUser u", Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    public AuthUser fetchAuthUserByMobileNumber(String mobileNumber) {
        if (StringUtils.isBlank(mobileNumber)) {
            return null;
        }
        try {
            return entityManager.createQuery("SELECT u from AuthUser u WHERE u.mobileNumber =?1",
                            AuthUser.class).
                    setParameter(1, mobileNumber).getSingleResult();
        } catch (Exception e) {
            LOG.error("Error", e);
            return null;
        }
    }

    public AuthUser fetchAuthUserByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        try {
            return entityManager.createQuery("SELECT u from AuthUser u WHERE u.userName =?1", AuthUser.class).
                    setParameter(1, userName).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public UserRole fetchUserRoleByRoleName(String roleName) {
        if (StringUtils.isBlank(roleName)) {
            return null;
        }
        try {
            return entityManager.createQuery("SELECT u from UserRole u WHERE u.roleName =?1", UserRole.class).
                    setParameter(1, roleName).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    private static final String COL_DDL = "\n\n//Column definitions for table %s in schema %s\n";
    private static final String CON_DDL = "\n\n//Constraint definitions for table %s in schema %s\n";
    private static final String INDEX_DDL = "\n\n//Constraint definitions for table %s in schema %s\n";

    private boolean isUpperCase(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public List<String> selectTableNames(String platform, String schemaName) throws Exception {
        String queryString;
        if (SupportedDBPlatform.HSQLDB.name().equalsIgnoreCase(platform)) {
            queryString = HS_SELECT_TABLES;
        } else {
            queryString = String.format(SELECT_TABLES, schemaName);
        }
        List<String> tableNames = new ArrayList<>();
        try {
            Query query = entityManager.createNativeQuery(queryString);
            List<Object> results = query.getResultList();
            for (Object result : results) {
                tableNames.add((String) result);
            }
            return tableNames;
        } catch (Exception e) {
            LOG.error("Error retrieving table names for : " + schemaName, e);
            return tableNames;
        }
    }

    public Feature fetchFeatureByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        try {
            return entityManager.createQuery("SELECT u from Feature u WHERE u.name =?1", Feature.class).
                    setParameter(1, name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public GenericJDBCRepository<BaseEntity> getRepository() {
        return repository;
    }

    @Transactional
    public <T extends BaseEntity> void save(T entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity); // Save a new entity
        } else {
            entityManager.merge(entity); // Update an existing entity
        }
    }

    public static final String HS_SELECT_TABLES = "SELECT table_name" +
            " FROM information_schema.tables" +
            " order by table_name;";

    public static final String SELECT_TABLES = "SELECT table_name" +
            " FROM information_schema.tables" +
            " WHERE table_schema = '%s' or table_schema = 'public'" + //this is for postgres
            " order by table_name;";

    public static final String ORG_ID = "orgId";
    public static final String TAX_AUTHORITY_NAME = "tax.authority.name";
    private static final Logger LOG = LoggerFactory.getLogger(RepositoryService.class);


}
