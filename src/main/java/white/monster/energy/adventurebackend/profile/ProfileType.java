package white.monster.energy.adventurebackend.profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/*
Enum to define different profile types and their access levels
*/


public enum ProfileType
{
    VISITOR, //standard costumer, can only view and book activities. not yet in use
    OPERATOR, // employee, can view their own booked activities and their own profile
    ADMIN; //manager, can view and manage all activities and bookings, and all profiles


    // helper method, verifies if the profile type in the current session has the required access level
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