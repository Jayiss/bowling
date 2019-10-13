package training.adv.bowling.impl.shike;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey{

	private Integer id;
	private Integer fid;
	
	public TurnKeyImpl(int id, int fid) {
		this.id=id;
		this.fid=fid;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Integer getForeignId() {
		return fid;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this==obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		TurnKeyImpl other = (TurnKeyImpl) obj;
		
		if (fid == null) {
			if (other.fid!=null) {
				return false;
			}
		}else if (!fid.equals(other.fid)) {
			return false;
		}
		
		if (id == null) {
			if (other.id !=null) {
				return false;
			}
		}else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
