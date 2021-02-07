class CreateTokens < ActiveRecord::Migration
  def self.up
    create_table :tokens do |t|
      t.column :team_id, :integer
      t.column :event_id, :integer
      t.column :created_at, :datetime
      t.column :trials_left, :integer, :default => 10
    end
  end

  def self.down
    drop_table :tokens
  end
end
