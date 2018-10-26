using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace OneTapHelpBackend
{
    public class Entity
    {
        public long lattitude;
        public long longitude;
        public long phoneNumber;
        public long dateIndex;

        public static String GetDataLine(Entity e)
        {
            return e.phoneNumber.ToString() + "_" + e.lattitude.ToString() + "_" + e.longitude.ToString()+"_"+e.dateIndex.ToString();
        }
        public static Entity ParseEntity(String s)
        {
            if (s.Length == 0)
            {
                return null;
            }
            String[] list = s.Split("_");
            if (list.Length != 4)
            {
                return null;
            }
            Entity entity = new Entity();
            try
            {
                entity.phoneNumber = long.Parse(list[0]);
            }
            catch
            {
                return null;
            }
            try
            {
                entity.lattitude = long.Parse(list[1]);
            }
            catch
            {
                return null;
            }
            try
            {
                entity.longitude = long.Parse(list[2]);
            }
            catch
            {
                return null;
            }
            try
            {
                entity.dateIndex = long.Parse(list[3]);
            }
            catch
            {
                return null;
            }
            return entity;
        }
    }
}
