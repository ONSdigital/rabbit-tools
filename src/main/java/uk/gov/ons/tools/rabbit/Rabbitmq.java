package uk.gov.ons.tools.rabbit;

import lombok.Data;
import net.sourceforge.cobertura.CoverageIgnore;

@Data
@CoverageIgnore
public class Rabbitmq {
    private String username;
    private String password;
    private String host;
    private int port;
    private String virtualHost;
    private String cron;
}
