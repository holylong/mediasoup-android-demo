/*
 *  Copyright (c) 2016 The WebRTC project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

#include "modules/video_coding/utility/default_video_bitrate_allocator.h"

#include <limits>
#include <memory>

#include "test/gtest.h"

namespace webrtc {
namespace {
uint32_t kMaxBitrateBps = 1000000;
uint32_t kMinBitrateBps = 50000;
uint32_t kMaxFramerate = 30;
}  // namespace

class DefaultVideoBitrateAllocatorTest : public ::testing::Test {
 public:
  DefaultVideoBitrateAllocatorTest() {}
  virtual ~DefaultVideoBitrateAllocatorTest() {}

  void SetUp() override {
    codec_.codecType = kVideoCodecVP8;
    codec_.minBitrate = kMinBitrateBps / 1000;
    codec_.maxBitrate = kMaxBitrateBps / 1000;
    codec_.maxFramerate = kMaxFramerate;
    allocator_.reset(new DefaultVideoBitrateAllocator(codec_));
  }

 protected:
  VideoCodec codec_;
  std::unique_ptr<DefaultVideoBitrateAllocator> allocator_;
};

TEST_F(DefaultVideoBitrateAllocatorTest, ZeroIsOff) {
  VideoBitrateAllocation allocation =
      allocator_->Allocate(VideoBitrateAllocationParameters(0, kMaxFramerate));
  EXPECT_EQ(0u, allocation.get_sum_bps());
}

TEST_F(DefaultVideoBitrateAllocatorTest, Inactive) {
  codec_.active = false;
  allocator_.reset(new DefaultVideoBitrateAllocator(codec_));
  VideoBitrateAllocation allocation =
      allocator_->Allocate(VideoBitrateAllocationParameters(1, kMaxFramerate));
  EXPECT_EQ(0u, allocation.get_sum_bps());
}

TEST_F(DefaultVideoBitrateAllocatorTest, CapsToMin) {
  VideoBitrateAllocation allocation =
      allocator_->Allocate(VideoBitrateAllocationParameters(1, kMaxFramerate));
  EXPECT_EQ(kMinBitrateBps, allocation.get_sum_bps());

  allocation = allocator_->Allocate(
      VideoBitrateAllocationParameters(kMinBitrateBps - 1, kMaxFramerate));
  EXPECT_EQ(kMinBitrateBps, allocation.get_sum_bps());

  allocation = allocator_->Allocate(
      VideoBitrateAllocationParameters(kMinBitrateBps, kMaxFramerate));
  EXPECT_EQ(kMinBitrateBps, allocation.get_sum_bps());
}

TEST_F(DefaultVideoBitrateAllocatorTest, CapsToMax) {
  VideoBitrateAllocation allocation = allocator_->Allocate(
      VideoBitrateAllocationParameters(kMaxBitrateBps, kMaxFramerate));
  EXPECT_EQ(kMaxBitrateBps, allocation.get_sum_bps());

  allocation = allocator_->Allocate(
      VideoBitrateAllocationParameters(kMaxBitrateBps + 1, kMaxFramerate));
  EXPECT_EQ(kMaxBitrateBps, allocation.get_sum_bps());

  allocation = allocator_->Allocate(VideoBitrateAllocationParameters(
      std::numeric_limits<uint32_t>::max(), kMaxFramerate));
  EXPECT_EQ(kMaxBitrateBps, allocation.get_sum_bps());
}

TEST_F(DefaultVideoBitrateAllocatorTest, GoodInBetween) {
  VideoBitrateAllocation allocation = allocator_->Allocate(
      VideoBitrateAllocationParameters(kMinBitrateBps + 1, kMaxFramerate));
  EXPECT_EQ(kMinBitrateBps + 1, allocation.get_sum_bps());

  allocation = allocator_->Allocate(
      VideoBitrateAllocationParameters(kMaxBitrateBps - 1, kMaxFramerate));
  EXPECT_EQ(kMaxBitrateBps - 1, allocation.get_sum_bps());
}
}  // namespace webrtc
