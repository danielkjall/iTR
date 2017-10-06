package com.intiro.itr.util.cache;

class CachedObject
{
   private long _timestamp = 0;
   private int _cacheTimeInSeconds;

   // Default cache time is 30 minutes
   private final int defaultCacheTimeInSeconds = 60 * 60;
   private Object _value;

   public CachedObject( Object val )
   {
      this._timestamp = System.currentTimeMillis();
      this._value = val;
      this._cacheTimeInSeconds = defaultCacheTimeInSeconds;
   }

   public CachedObject( Object val, int cacheTimeInSeconds )
   {
      this._timestamp = System.currentTimeMillis();
      this._cacheTimeInSeconds = cacheTimeInSeconds;
      this._value = val;
   }

   public boolean hasExpired()
   {
      long expiryTime = _timestamp + _cacheTimeInSeconds * 1000;
      return System.currentTimeMillis() > expiryTime;
   }

   public void addExpire( int seconds )
   {
      long expiryTime = _timestamp + _cacheTimeInSeconds * 1000;
      long current = System.currentTimeMillis();
      long left = expiryTime - current;
      if ( left < seconds * 1000 )
      {
         _cacheTimeInSeconds += seconds - Math.floor( left / 1000 );
      }
   }

   public Object getValue()
   {
      return _value;
   }
}