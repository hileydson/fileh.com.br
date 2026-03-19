package br.com.fileh.auth.security.services;

import br.com.fileh.auth.model.SubUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long tenantId; // Usuario ID
    private Long entidadeId; // Entidade ID binding
    private String username;
    private String name;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, Long tenantId, Long entidadeId, String username, String name, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.tenantId = tenantId;
        this.entidadeId = entidadeId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(SubUsuario subUsuario) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (Boolean.TRUE.equals(subUsuario.getIsAdm())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            if (Boolean.TRUE.equals(subUsuario.getModuloVenda())) 
                authorities.add(new SimpleGrantedAuthority("ROLE_VENDAS"));
            if (Boolean.TRUE.equals(subUsuario.getModuloCaixa())) 
                authorities.add(new SimpleGrantedAuthority("ROLE_FLUXO_CAIXA"));
        }

        Long tenantId = null;
        String email = null;
        if (subUsuario.getUsuario() != null) {
            tenantId = subUsuario.getUsuario().getId();
            email = subUsuario.getUsuario().getEmail();
        }

        return new UserDetailsImpl(
                subUsuario.getId(),
                tenantId,
                subUsuario.getEntidadeId(),
                subUsuario.getLogin(),
                subUsuario.getNome(),
                email,
                subUsuario.getSenha(),
                authorities);
    }

    public Long getId() { return id; }
    public Long getTenantId() { return tenantId; }
    public Long getEntidadeId() { return entidadeId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override
    public String getPassword() { return password; }
    @Override
    public String getUsername() { return username; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
