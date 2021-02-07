class AddAccessKeyToTeams < ActiveRecord::Migration
  def self.up
    add_column :teams, :access_key, :string
  end

  def self.down
    remove_column :teams, :access_key
  end
end
