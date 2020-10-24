package com.cwality;

public class Match<T> {
    
    public T giver;
    public T taker;
    
    public Match(T giver, T taker) {
        this.giver=giver;
        this.taker=taker;
    }

    @Override
    public String toString() {
        return giver+ " -> " + taker;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((giver == null) ? 0 : giver.hashCode());
        result = prime * result + ((taker == null) ? 0 : taker.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Match<?> other = (Match<?>) obj;
        if (giver == null) {
            if (other.giver != null)
                return false;
        } else if (!giver.equals(other.giver))
            return false;
        if (taker == null) {
            if (other.taker != null)
                return false;
        } else if (!taker.equals(other.taker))
            return false;
        return true;
    }

}
