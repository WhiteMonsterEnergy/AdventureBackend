package white.monster.energy.adventurebackend.profile;

public enum ProfileType {
    VISITOR,
    OPERATOR,
    MANAGER,
    ADMIN;

    boolean isEmployee()
    {
        return this.ordinal() > 0;
    }

    boolean isVisitor()
    {
        return !this.isEmployee();
    }
}