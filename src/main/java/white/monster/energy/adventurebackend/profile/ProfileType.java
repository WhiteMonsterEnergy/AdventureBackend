package white.monster.energy.adventurebackend.profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public enum ProfileType
{
    VISITOR,
    OPERATOR,
    ADMIN;

    public boolean verifyAccessLevel(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false); // attempt to retrieve active session
        if(session == null) return false;
        try
        {
            Profile profile = (Profile) session.getAttribute("profile"); // get profile from active session
            return profile.getType().ordinal() >= this.ordinal(); // check profile type against access required
        }
        catch (Exception e) {return false;}
    }
}