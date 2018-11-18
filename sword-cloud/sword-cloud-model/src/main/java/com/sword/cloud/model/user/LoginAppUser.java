package com.sword.cloud.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * spring security当前登录对象
 */
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class LoginAppUser extends AppUser implements UserDetails {

	private static final long serialVersionUID = 1753977564987556640L;

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new HashSet<>();
		Set<String> permissions = Sets.newHashSet();
		permissions.add("back:permission:save");
		permissions.add("back:permission:update");
		permissions.add("back:permission:delete");
		if (!CollectionUtils.isEmpty(permissions)) {
			permissions.forEach(per -> {
				collection.add(new SimpleGrantedAuthority(per));
			});
		}
		return collection;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return getEnabled();
	}
}
