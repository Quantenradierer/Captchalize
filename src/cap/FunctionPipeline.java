package cap;

import cap.db.jpa.Managers;
import cap.db.jpa.Slot;
import cap.db.jpa.SlotNotFoundException;

import java.util.Iterator;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class FunctionPipeline {
    private cap.db.jpa.FunctionPipeline model = null;
    private String name = "";
    private Iterator<Slot> currentSlot = null;

    public FunctionPipeline(String name) {
        this.name = name;
    }

    public void load() {
        this.model = Managers.functionPipelineManager.getOrCreate(name);
    }

    public boolean register(ISlotFunction function) {
        assert this.model != null;
        assert this.currentSlot == null;

        Slot slot = Managers.slotManager.find(function.getName(), function.getId());
        if (slot != null) {
            this.model.getSlots().add(slot);
            return true;
        }

        return false;
    }

    public boolean unregister(ISlotFunction function) {
        assert this.model != null;
        assert this.currentSlot == null;

        Slot slot = Managers.slotManager.find(function.getName(), function.getId());
        if (slot != null) {
            this.model.getSlots().remove(slot);
            return true;
        }

        return false;
    }

    public <T> ISlotFunction<T> next() {
        if (currentSlot == null) {
            this.currentSlot = this.model.getSlots().iterator();
        }

        Slot slot = this.currentSlot.next();
        SlotFunction<T> function = null;

        try {
            function = (SlotFunction<T>)Class.forName("cap.slots." + slot.getClassName()).newInstance();
        } catch(ClassNotFoundException exception) {
            return null;
        } catch (IllegalAccessException exception) {
            return null;
        } catch (InstantiationException exception) {
            return null;
        }

        function.create(slot);
        return function;
    }
}