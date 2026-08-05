package remote_preparation;

class UserBuilder extends Builder<UserBuilder> {

    private int age;

    public UserBuilder age(int age) {
        this.age = age;
        return this;
    }

    public User build() {
        return new User(name, age);
    }
}
