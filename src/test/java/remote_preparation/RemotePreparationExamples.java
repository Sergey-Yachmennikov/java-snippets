package remote_preparation;

import org.junit.jupiter.api.Test;

 class RemotePreparationExamples {


    @Test
    void testBuilder() {
        User user = new UserBuilder()
                .name("John")
                .age(30)
                .build();

        System.out.println(user);
    }

}
