class AddRenewWeeklyToEvents < ActiveRecord::Migration
  def self.up
    add_column :events, :renew_weekly, :boolean, :default => 1
  end

  def self.down
    remove_column :events, :renew_weekly
  end
end
