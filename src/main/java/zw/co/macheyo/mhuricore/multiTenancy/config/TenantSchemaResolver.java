package zw.co.macheyo.mhuricore.multiTenancy.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.multiTenancy.TenantContext;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String t =  TenantContext.getCurrentTenant();
        if(t!=null){
            return t;
        } else {
            return "public";
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
