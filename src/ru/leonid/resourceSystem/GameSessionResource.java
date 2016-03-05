
package ru.leonid.resourceSystem;

import ru.leonid.base.Resource;

public class GameSessionResource implements Resource{
    private int period;

    public GameSessionResource() {
    }

    public GameSessionResource(int period) {
        this.period = period;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
    
}
