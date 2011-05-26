/*
 *        JacORB - a free Java ORB
 *
 *   Copyright (C) 1999-2004 Gerald Brose
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Library General Public
 *   License as published by the Free Software Foundation; either
 *   version 2 of the License, or (at your option) any later version.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this library; if not, write to the Free
 *   Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */

package org.jacorb.orb.nio;

import org.jacorb.config.*;
import org.omg.ETF.Profile;
import org.jacorb.orb.iiop.IIOPListener;
import org.jacorb.orb.iiop.IIOPAddress;
import org.jacorb.orb.iiop.IIOPProfile;

/**
 * @author Ciju John
 * @version $Id$
 *
 * This class is identical to the iiop.IIOPFactories except for the static
 *  initialization bit.
 */
public class NIOFactories
  extends org.jacorb.orb.etf.FactoriesBase
{
  static {
    connectionClz = ClientNIOConnection.class;
    listenerClz = IIOPListener.class;
    profileClz = IIOPProfile.class;
    addressClz = IIOPAddress.class;
  }

  public int profile_tag() {
    return org.omg.IOP.TAG_INTERNET_IOP.value;
  }

  public Profile decode_corbaloc (String corbaloc) {
    int colon = corbaloc.indexOf (':');
    String token = corbaloc.substring (0,colon).toLowerCase();
    if (token.length() == 0 ||
        "iiop".equals (token) ||
        "ssliop".equals (token)) {
      IIOPProfile result = new IIOPProfile(corbaloc);
      try {
        result.configure(configuration);
      }
      catch(ConfigurationException e) {
        throw new org.omg.CORBA.INTERNAL("ConfigurationException: " + e);
      }

      return result;
    }
    return null;
  }

  public int match_tag(String address) {
    if (address == null) {
      return -1;
    }
    int colon = address.indexOf (':');
    String token = address.substring (0,colon).toLowerCase();
    if ("iiop".equals (token) || "ssliop".equals (token)) {
      return colon+1;
    }
    return -1;
  }

}
