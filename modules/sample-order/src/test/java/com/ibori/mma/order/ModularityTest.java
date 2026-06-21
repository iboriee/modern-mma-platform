package com.ibori.mma.order;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTest {

    private final ApplicationModules modules = ApplicationModules.of(OrderTestApplication.class);
    @Test
    void verifyModuleStructure() {
        modules.verify();   // domain → infra 같은 금지된 의존 방향이 있으면 여기서 실패
    }

    @Test
    void writeDocumentation() {
        new Documenter(modules)
                .writeModulesAsPlantUml()      // 모듈 구조 다이어그램 (.puml)
                .writeIndividualModulesAsPlantUml();  // 모듈별 상세 다이어그램
    }
}