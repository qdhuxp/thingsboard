/**
 * Copyright © 2016-2017 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.dao.model.sql;

import com.datastax.driver.core.utils.UUIDs;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.thingsboard.server.common.data.id.UserCredentialsId;
import org.thingsboard.server.common.data.id.UserId;
import org.thingsboard.server.common.data.security.UserCredentials;
import org.thingsboard.server.dao.model.BaseEntity;
import org.thingsboard.server.dao.model.ModelConstants;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = ModelConstants.USER_CREDENTIALS_COLUMN_FAMILY_NAME)
@EqualsAndHashCode
@ToString
public final class UserCredentialsEntity implements BaseEntity<UserCredentials> {

    @Transient
    private static final long serialVersionUID = -3989724854149114846L;

    @Id
    @Column(name = ModelConstants.ID_PROPERTY)
    private UUID id;

    @Column(name = ModelConstants.USER_CREDENTIALS_USER_ID_PROPERTY, unique = true)
    private UUID userId;

    @Column(name = ModelConstants.USER_CREDENTIALS_ENABLED_PROPERTY)
    private boolean enabled;

    @Column(name = ModelConstants.USER_CREDENTIALS_PASSWORD_PROPERTY)
    private String password;

    @Column(name = ModelConstants.USER_CREDENTIALS_ACTIVATE_TOKEN_PROPERTY, unique = true)
    private String activateToken;

    @Column(name = ModelConstants.USER_CREDENTIALS_RESET_TOKEN_PROPERTY, unique = true)
    private String resetToken;

    public UserCredentialsEntity() {
        super();
    }

    public UserCredentialsEntity(UserCredentials userCredentials) {
        if (userCredentials.getId() != null) {
            this.id = userCredentials.getId().getId();
        }
        if (userCredentials.getUserId() != null) {
            this.userId = userCredentials.getUserId().getId();
        }
        this.enabled = userCredentials.isEnabled();
        this.password = userCredentials.getPassword();
        this.activateToken = userCredentials.getActivateToken();
        this.resetToken = userCredentials.getResetToken();
    }

    @Override
    public UserCredentials toData() {
        UserCredentials userCredentials = new UserCredentials(new UserCredentialsId(id));
        userCredentials.setCreatedTime(UUIDs.unixTimestamp(id));
        if (userId != null) {
            userCredentials.setUserId(new UserId(userId));
        }
        userCredentials.setEnabled(enabled);
        userCredentials.setPassword(password);
        userCredentials.setActivateToken(activateToken);
        userCredentials.setResetToken(resetToken);
        return userCredentials;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}