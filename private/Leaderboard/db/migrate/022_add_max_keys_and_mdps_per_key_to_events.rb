class AddMaxKeysAndMdpsPerKeyToEvents < ActiveRecord::Migration
  def self.up
    add_column :events, :keys_per_week, :integer
    add_column :events, :mdps_per_key,  :integer
  end

  def self.down
    remove_column :events, :keys_per_week
    remove_column :events, :mdps_per_key
  end
end
