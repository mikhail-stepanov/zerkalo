package com.tochka.database.entity.auto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import com.tochka.database.entity.TchUserSession;

/**
 * Class _TchUser was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _TchUser extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<UUID> ACCOUNT_ID = Property.create("accountId", UUID.class);
    public static final Property<LocalDateTime> CREATED_DATE = Property.create("createdDate", LocalDateTime.class);
    public static final Property<LocalDateTime> DELETED_DATE = Property.create("deletedDate", LocalDateTime.class);
    public static final Property<String> EMAIL = Property.create("email", String.class);
    public static final Property<String> LOGIN = Property.create("login", String.class);
    public static final Property<LocalDateTime> MODIFIED_DATE = Property.create("modifiedDate", LocalDateTime.class);
    public static final Property<String> PASSWORD = Property.create("password", String.class);
    public static final Property<List<TchUserSession>> TCH_USER_SESSIONS = Property.create("tchUserSessions", List.class);

    public void setAccountId(UUID accountId) {
        writeProperty("accountId", accountId);
    }
    public UUID getAccountId() {
        return (UUID)readProperty("accountId");
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        writeProperty("createdDate", createdDate);
    }
    public LocalDateTime getCreatedDate() {
        return (LocalDateTime)readProperty("createdDate");
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        writeProperty("deletedDate", deletedDate);
    }
    public LocalDateTime getDeletedDate() {
        return (LocalDateTime)readProperty("deletedDate");
    }

    public void setEmail(String email) {
        writeProperty("email", email);
    }
    public String getEmail() {
        return (String)readProperty("email");
    }

    public void setLogin(String login) {
        writeProperty("login", login);
    }
    public String getLogin() {
        return (String)readProperty("login");
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        writeProperty("modifiedDate", modifiedDate);
    }
    public LocalDateTime getModifiedDate() {
        return (LocalDateTime)readProperty("modifiedDate");
    }

    public void setPassword(String password) {
        writeProperty("password", password);
    }
    public String getPassword() {
        return (String)readProperty("password");
    }

    public void addToTchUserSessions(TchUserSession obj) {
        addToManyTarget("tchUserSessions", obj, true);
    }
    public void removeFromTchUserSessions(TchUserSession obj) {
        removeToManyTarget("tchUserSessions", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<TchUserSession> getTchUserSessions() {
        return (List<TchUserSession>)readProperty("tchUserSessions");
    }


}
