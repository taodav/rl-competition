class CreateKeysAvailables < ActiveRecord::Migration
  def self.up
    create_table :keys_availables do |t|
      t.column :team_id,    :integer
      t.column :event_id,   :integer
      t.column :updated_at, :datetime
      t.column :num,        :integer
    end
  end

  def self.down
    drop_table :keys_availables
  end
end
