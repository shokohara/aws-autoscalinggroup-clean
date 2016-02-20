package com.github.skohar.awsautoscalingclean

import com.amazonaws.services.autoscaling.AmazonAutoScalingClient
import com.amazonaws.services.autoscaling.model.DeleteAutoScalingGroupRequest

import scala.collection.JavaConversions._

object Worker {

  val autoScalingClient = new AmazonAutoScalingClient()

  def detachAutoScalingGroup(deleteCount: Int, dryRun: Boolean) = {
    autoScalingClient.describeAutoScalingGroups.getAutoScalingGroups.filter(_.getLoadBalancerNames.isEmpty).filter(_.getInstances.isEmpty).sortBy(_.getCreatedTime).take(deleteCount).foreach { asg =>
      if (!dryRun) {
        autoScalingClient.deleteAutoScalingGroup(new DeleteAutoScalingGroupRequest().withAutoScalingGroupName(asg.getAutoScalingGroupName))
      }
      println(s"Delete AutoScalingGroup(${asg.getAutoScalingGroupName})")
    }
  }
}
