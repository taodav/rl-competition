class ChangeFloatsToDoubles < ActiveRecord::Migration
  def self.up
    change_column :summary_results, :total_return, :decimal, {:precision => 63, :scale => 30}
  end

  def self.down
    change_column :summary_results, :total_return, :float
  end
end
