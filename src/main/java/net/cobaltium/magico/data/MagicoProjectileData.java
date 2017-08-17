package net.cobaltium.magico.data;

import net.cobaltium.magico.MagicoKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class MagicoProjectileData extends AbstractData<MagicoProjectileData, ImmutableMagicoProjectileData> {

    private boolean blockDamage;

    protected MagicoProjectileData() {
        this(false);
    }
    protected MagicoProjectileData(boolean value) {
        this.blockDamage = value;
        registerGettersAndSetters();
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(MagicoKeys.DOES_BLOCK_DAMAGE, () -> this.blockDamage);
        registerFieldSetter(MagicoKeys.DOES_BLOCK_DAMAGE, blockDamage -> this.blockDamage = blockDamage);
        registerKeyValue(MagicoKeys.DOES_BLOCK_DAMAGE, this::blockDamage);
    }

    public  Value<Boolean> blockDamage() {
        return Sponge.getRegistry().getValueFactory().createValue(MagicoKeys.DOES_BLOCK_DAMAGE, blockDamage);
    }

    public boolean doesBlockDamage() {
        return get(MagicoKeys.DOES_BLOCK_DAMAGE).get();
    }
    public void setBlockDamage(boolean blockDamage) {
        set(MagicoKeys.DOES_BLOCK_DAMAGE, blockDamage);
    }

    @Override
    public Optional<MagicoProjectileData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<MagicoProjectileData> data_ = dataHolder.get(MagicoProjectileData.class);
        if(data_.isPresent()) {
            MagicoProjectileData data = data_.get();
            MagicoProjectileData mergedData = overlap.merge(this, data);
            setBlockDamage(mergedData.doesBlockDamage());
        }
        return Optional.of(this);
    }

    @Override
    public Optional<MagicoProjectileData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<MagicoProjectileData> from(DataView view) {
        if(view.contains(MagicoKeys.DOES_BLOCK_DAMAGE.getQuery())) {
            setBlockDamage(view.getBoolean(MagicoKeys.DOES_BLOCK_DAMAGE.getQuery()).get());
            return Optional.of(this);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public MagicoProjectileData copy() {
        return new MagicoProjectileData(doesBlockDamage());
    }

    @Override
    public ImmutableMagicoProjectileData asImmutable() {
        return new ImmutableMagicoProjectileData(doesBlockDamage());
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

}

