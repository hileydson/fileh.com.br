package br.com.fileh.auth.payload.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private Long tenantId;
    private Long entidadeId;
    private String username;
    private String name;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, Long tenantId, Long entidadeId, String username, String name, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.tenantId = tenantId;
        this.entidadeId = entidadeId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    // Getters
    public String getToken() { return token; }
    public String getType() { return type; }
    public Long getId() { return id; }
    public Long getTenantId() { return tenantId; }
    public Long getEntidadeId() { return entidadeId; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<String> getRoles() { return roles; }
}
