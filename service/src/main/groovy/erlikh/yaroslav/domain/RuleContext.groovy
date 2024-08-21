package erlikh.yaroslav.domain

import java.time.LocalTime

record RuleContext (
        LocalTime time = null,
        String device = null
) {}