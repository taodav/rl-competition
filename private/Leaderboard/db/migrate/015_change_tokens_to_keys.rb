class ChangeTokensToKeys < ActiveRecord::Migration
  def self.up
    drop_table :tokens
    create_table :team_keys do |t|
      t.column :team_id, :integer
      t.column :event_id, :integer
      t.column :created_at, :datetime
      t.column :current_mdp, :integer, :default => 0
      t.column :key_value, :string
    end
  end

  def self.down
    
    create_table "tokens", :force => true do |t|
      t.column "team_id",     :integer
      t.column "event_id",    :integer
      t.column "created_at",  :datetime
      t.column "trials_left", :integer,  :default => 10
    end
    drop_table :team_keys
  end
end
